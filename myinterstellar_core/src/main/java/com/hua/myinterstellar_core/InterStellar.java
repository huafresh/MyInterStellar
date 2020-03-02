package com.hua.myinterstellar_core;

import android.content.Context;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 11:05
 */

public class InterStellar {

    public static void init(Context context){
        RemoteServiceManager.get().init(context);
    }

    public static void registerRemoteService(Class<?> serviceInterface, Object serviceImpl) {
        RemoteServiceManager.get().registerRemoteService(serviceInterface.getCanonicalName(), serviceImpl);
    }

    public static <T> T getRemoteService(Class<T> serviceInterface) {
        return RemoteServiceManager.get().getRemoteService(serviceInterface);
    }

    public static void unRegisterRemoteService(Class<?> serviceInterface){
        RemoteServiceManager.get().unRegisterRemoteService(serviceInterface.getCanonicalName());
    }
}
