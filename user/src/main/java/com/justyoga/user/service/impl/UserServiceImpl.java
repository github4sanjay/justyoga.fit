package com.justyoga.user.service.impl;

import com.justyoga.user.cache.service.FirebaseUIDVsUUIDCacheService;
import com.justyoga.user.cache.service.impl.core.UserCacheService;
import com.justyoga.user.domain.dao.UserRepository;
import com.justyoga.user.domain.model.mysql.RoleEntity;
import com.justyoga.user.domain.model.mysql.User;
import com.justyoga.user.service.interfaces.UserService;
import com.justyoga.user.web.config.WebConfig;
import com.justyoga.util.dto.user.RoleDTO;
import com.justyoga.util.dto.user.UserDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import java.util.*;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = UserServiceImpl.NAME)
public class UserServiceImpl implements UserService {

    public static final String NAME = "UserService";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userDao;
    private final ModelMapper modelMapper;

    private final RolesService rolesService;
    private final UserCacheService userCacheService;
    private final FirebaseUIDVsUUIDCacheService firebaseUIDVsUUIDCacheService;

    @Autowired
    public UserServiceImpl(
            UserRepository userDao,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            RolesService rolesService,
            UserCacheService userCacheService,
            FirebaseUIDVsUUIDCacheService firebaseUIDVsUUIDCacheService) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.rolesService = rolesService;
        this.userCacheService = userCacheService;
        this.firebaseUIDVsUUIDCacheService = firebaseUIDVsUUIDCacheService;
    }

    private void updateCache(UserDTO map) {
        userCacheService.put(map.getId(), map);
        firebaseUIDVsUUIDCacheService.put(map.getFirebaseUid(), map.getId());
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User userLoaded = userDao.findByFirebaseUid(userDTO.getFirebaseUid());
        if (userLoaded == null) {
            User userEntity = new User();
            userEntity.setFirebaseUid(userDTO.getFirebaseUid());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setPhoneNumber(userDTO.getPhoneNumber());
            userEntity.setProfilePic(userDTO.getProfilePic());
            userEntity.setName(userDTO.getName());
            userEntity.setEmailVerified(true);
            userEntity.setProviderId(userDTO.getProviderId());
            userEntity.setAuthorities(rolesService.getUserRoles());
            userEntity.setPassword(
                    UUID.randomUUID()
                            .toString()); // password so for now generation of password is OK
            User user = userDao.save(userEntity);
            UserDTO map = modelMapper.map(user, UserDTO.class);
            updateCache(map);
            return map;
        } else {
            throw new AppException(
                    "User already exist with this firebase id", AppStatusCode.USER_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional
    public UserDTO createOrUpdate(UserDTO userDTO) {
        Optional<User> userOptional = userDao.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setDescription(userDTO.getDescription());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            User save = userDao.save(user);
            UserDTO map = modelMapper.map(save, UserDTO.class);
            updateCache(map);
            return map;
        } else {
            throw new AppException("User not found", AppStatusCode.USER_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOS = new ArrayList<>();
        userDao.streamAllUser()
                .forEach(
                        user -> {
                            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                            userDTOS.add(userDTO);
                        });
        return userDTOS;
    }

    @Override
    public UserDTO getByFirebaseUid(String firebaseUid) {
        if (firebaseUid != null) {
            UUID uuid = firebaseUIDVsUUIDCacheService.get(firebaseUid);
            if (uuid == null) throw new AppException(AppStatusCode.USER_NOT_FOUND);
            return this.getUserById(uuid);
        } else {
            throw new AppException(AppStatusCode.USER_NOT_FOUND);
        }
    }

    @Override
    public UserDTO getUserById(UUID id) {
        UserDTO userCacheDTO = userCacheService.get(id);
        if (userCacheDTO != null) return userCacheDTO;
        else {
            Optional<User> userOptional = userDao.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDTO map = modelMapper.map(user, UserDTO.class);
                updateCache(map);
                return map;
            } else throw new AppException(AppStatusCode.USER_NOT_FOUND);
        }
    }

    @Override
    public boolean isAdmin(UUID id) {
        UserDTO userById = getUserById(id);
        if (userById == null) return false;
        List<RoleDTO> authorities = userById.getAuthorities();
        if (authorities == null) return false;
        for (RoleDTO roleEntity : authorities) {
            if (roleEntity.getAuthority().equals(RolesService.ROLE_ADMIN)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDTO updateProfilePic(String url, UUID id) {
        Optional<User> byId = userDao.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setProfilePic(url);
            User save = userDao.save(user);
            UserDTO map = modelMapper.map(save, UserDTO.class);
            updateCache(map);
            return map;
        }
        return null;
    }

    @Override
    public UserDTO updateCoverPic(String url, UUID userId) {
        Optional<User> byId = userDao.findById(userId);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setCoverPic(url);
            User save = userDao.save(user);
            UserDTO map = modelMapper.map(save, UserDTO.class);
            updateCache(map);
            return map;
        }
        return null;
    }

    @Override
    public PageDTO<UserDTO> findAll(List<UUID> id, Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        Page<User> users = userDao.findAllByIdIn(id, pageable.get());
        List<UserDTO> list = new ArrayList<>();
        users.forEach(trainer -> list.add(modelMapper.map(trainer, UserDTO.class)));
        return PageDTO.<UserDTO>builder()
                .content(list)
                .first(users.isFirst())
                .hasContent(users.hasContent())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .last(users.isLast())
                .number(users.getNumber())
                .numberOfElements(users.getNumberOfElements())
                .size(users.getSize())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<UserDTO> findAll(Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        Page<User> users = userDao.findAll(pageable.get());
        List<UserDTO> list = new ArrayList<>();
        users.getContent().forEach(trainer -> list.add(modelMapper.map(trainer, UserDTO.class)));
        return PageDTO.<UserDTO>builder()
                .content(list)
                .first(users.isFirst())
                .hasContent(users.hasContent())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .last(users.isLast())
                .number(users.getNumber())
                .numberOfElements(users.getNumberOfElements())
                .size(users.getSize())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public UserDTO createAdmin(UUID userId) {
        Optional<User> userOptional = userDao.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<RoleEntity> adminRoles = rolesService.getAdminRoles();
            user.setAuthorities(adminRoles);
            User save = userDao.save(user);
            UserDTO map = modelMapper.map(save, UserDTO.class);
            updateCache(map);
            return map;
        } else {
            throw new AppException("User not found", AppStatusCode.NOT_FOUND);
        }
    }

    @Override
    public void deleteUserByFirebaseUUID(String firebaseUUID) {
        User byFirebaseUid = userDao.findByFirebaseUid(firebaseUUID);
        if (byFirebaseUid == null) return;
        byFirebaseUid.setAuthorities(Collections.emptyList());
        userDao.deleteById(byFirebaseUid.getId());
    }
}
