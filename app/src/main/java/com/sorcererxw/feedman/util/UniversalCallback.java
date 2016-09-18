package com.sorcererxw.feedman.util;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/17
 */
public interface UniversalCallback<T> {
    T call(Object... args);
}
