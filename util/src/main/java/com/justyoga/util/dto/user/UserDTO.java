package com.justyoga.util.dto.user;

import com.justyoga.util.dto.BaseDTO;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserDTO extends BaseDTO implements Serializable {

    @NotEmpty private String firebaseUid;
    private String name;
    @NotEmpty private String email;
    private Boolean emailVerified;
    private String profilePic;
    private String coverPic;
    private String phoneNumber;
    private String providerId;
    private String password;
    private String description;
    private List<RoleDTO> authorities;
}
