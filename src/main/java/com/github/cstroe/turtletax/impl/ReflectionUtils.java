package com.github.cstroe.turtletax.impl;

/**
 * Created by cosmin on 2/16/17.
 */
public class ReflectionUtils {
    public static boolean match(Object o1, Class<?> c1) {
        return c1.isAssignableFrom(o1.getClass());
    }
}
