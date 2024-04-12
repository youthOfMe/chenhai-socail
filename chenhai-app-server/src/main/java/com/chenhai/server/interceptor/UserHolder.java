package com.chenhai.server.interceptor;

import com.chenhai.model.domain.User;

/**
 * 工具类: 实现threadlocal存储数据的方法
 */
public class UserHolder {

    private static ThreadLocal<User> t1 = new ThreadLocal<>();

    // 将用户对象, 存入Threadlocal
    public static void set(User user) {
        t1.set(user);
    }

    // 从当前线程, 获取用户对象
    public static User get() {
        return t1.get();
    }

    // 从当前线程, 获取用户对象的ID
    public static Long getUserId() {
        return t1.get().getId();
    }

    // 从当前线程, 获取用户对象的手机号码
    public static String getMobile() {
        return t1.get().getMobile();
    }

    // 清空
    public static void remove() {
        t1.remove();
    }
}
