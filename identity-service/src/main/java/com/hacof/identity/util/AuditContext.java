package com.hacof.identity.util;

import com.hacof.identity.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuditContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
