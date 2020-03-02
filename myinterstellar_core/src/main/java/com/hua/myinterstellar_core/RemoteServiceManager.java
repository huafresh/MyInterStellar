package com.hua.myinterstellar_core;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-02-29 21:47
 */
@SuppressWarnings("unchecked")
class RemoteServiceManager {
    private HashMap<String, Object> serviceCache = new HashMap<>();
    private IDispatcher dispatcher;
    private Application context;

    static RemoteServiceManager get() {
        return Holder.S_INSTANCE;
    }

    private static final class Holder {
        private static final RemoteServiceManager S_INSTANCE = new RemoteServiceManager();
    }

    private RemoteServiceManager() {
    }

    void init(Context context) {
        this.context = (Application) context.getApplicationContext();
    }

    private void ensureDispatcher() {
        if (dispatcher == null) {
            Cursor cursor = context.getContentResolver().query(StellarContentProvider.URI, null
                    , null, null, null);
            if (cursor != null) {
                IBinder binder = StellarMatrix.resolveBinder(cursor);
                if (binder != null) {
                    dispatcher = IDispatcher.Stub.asInterface(binder);
                    cursor.close();
                }
            }
        }
        if (dispatcher == null) {
            throw new StellarException("IDispatcher init failed");
        }
    }

    void registerRemoteService(String name, Object serviceImpl) {
        serviceCache.put(name, serviceImpl);
        ensureDispatcher();
        try {
            BinderBean binderBean = new BinderBean(ProcessUtil.getProcessName(context),
                    Transfer.get().asBinder());
            dispatcher.registerRemoteService(name, binderBean);
        } catch (RemoteException e) {
            throw new StellarException("registerRemoteService failed.", e);
        }
    }

    void unRegisterRemoteService(String name){
        serviceCache.remove(name);
        if (dispatcher != null) {
            try {
                dispatcher.unRegisterRemoteService(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    <T> T getRemoteService(Class<T> serviceInterface) {
        return this.getRemoteService(serviceInterface.getCanonicalName(), serviceInterface);
    }

    @Nullable
    <T> T getRemoteService(String name, Class<T> serviceInterface) {
        try {
            ensureDispatcher();
            BinderBean binderBean = dispatcher.getTransferInfo(name);
            Object proxy = Proxy.newProxyInstance(serviceInterface.getClassLoader(),
                    new Class[]{serviceInterface},
                    new InvocationHandler(name, Transfer.asInterface(binderBean.getTransfer())));
            return (T) proxy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Object getStubService(String name) {
        return serviceCache.get(name);
    }
}
