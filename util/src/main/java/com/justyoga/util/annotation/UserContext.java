package com.justyoga.util.annotation;

import java.util.UUID;

public class UserContext {
    private static ThreadLocal<UserContext> context = new ThreadLocal<>();
    private UUID userId;

    public UserContext(UserContext source) {
        this.userId = source.getUserId();
    }

    private UserContext() {}

    public static UserContext current() {
        if (context.get() != null) {
            return context.get();
        } else {
            synchronized (UserContext.class) {
                if (context.get() != null) {
                    return context.get();
                } else {
                    UserContext uc = new UserContext();
                    context.set(uc);
                    return context.get();
                }
            }
        }
    }

    public static void clear() {
        if (context != null) {
            context.remove();
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
