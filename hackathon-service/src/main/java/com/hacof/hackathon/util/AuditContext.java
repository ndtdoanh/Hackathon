package com.hacof.hackathon.util;

import org.springframework.stereotype.Component;

import com.hacof.hackathon.entity.User;

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
