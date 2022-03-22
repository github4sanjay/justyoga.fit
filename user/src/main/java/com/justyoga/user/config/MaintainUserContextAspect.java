package com.justyoga.user.config;

import com.justyoga.util.annotation.UserContext;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class MaintainUserContextAspect {

    @Pointcut("@annotation(com.justyoga.util.annotation.MaintainUserContext)")
    public void methodWithUserContextRequirement() {}

    @Around("methodWithUserContextRequirement()")
    public Object maintainUserContext(ProceedingJoinPoint jointPoint) throws Throwable {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String header = request.getHeader("X-Authorization-UUID");
            if (header != null) {
                UUID uid = UUID.fromString(header);
                UserContext context = UserContext.current();
                context.setUserId(uid);
            }
        }
        Object object = jointPoint.proceed();
        UserContext.clear();
        return object;
    }
}
