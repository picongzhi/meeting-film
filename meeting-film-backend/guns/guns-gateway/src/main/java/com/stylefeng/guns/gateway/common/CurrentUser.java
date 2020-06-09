package com.stylefeng.guns.gateway.common;

public class CurrentUser {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveUserId(String userId) {
        THREAD_LOCAL.set(userId);
    }

    public static String getCurrentUserId() {
        return THREAD_LOCAL.get();
    }
}
