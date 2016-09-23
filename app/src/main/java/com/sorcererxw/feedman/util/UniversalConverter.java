package com.sorcererxw.feedman.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/23
 */

public class UniversalConverter<E, T> {

    public interface Converter<E, T> {
        T convert(E origin);
    }

    private Converter<E, T> mConverter;

    public UniversalConverter(@NonNull Converter<E, T> converter) {
        mConverter = converter;
    }

    public T convert(E e) {
        return mConverter.convert(e);
    }

    public List<T> convertList(List<E> originList) {
        List<T> list = new ArrayList<>();
        for (E e : originList) {
            list.add(mConverter.convert(e));
        }
        return list;
    }

    public static <E, T> T convert(E origin, Converter<E, T> converter) {
        return converter.convert(origin);
    }

    public static <E, T> List<T> convertList(List<E> origin, Converter<E, T> converter) {
        List<T> list = new ArrayList<>();
        for (E e : origin) {
            list.add(converter.convert(e));
        }
        return list;
    }

}
