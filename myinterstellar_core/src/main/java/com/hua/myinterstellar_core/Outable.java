package com.hua.myinterstellar_core;

import android.os.Parcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-02 17:20
 */
public interface Outable<T> {
    void copy(T source);
}
