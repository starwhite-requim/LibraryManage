package com.se.librarymanagesystem.common;

/**
 * 编写通用工具类，封装ThreadLocal
 */
public class CurrentId {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void SetId(Long id){
        threadLocal.set(id);
    }

    public static Long GetId(){
        return threadLocal.get();
    }
}