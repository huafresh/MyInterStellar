package com.hua.myinterstellar_core;

import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-02-29 22:37
 */

class Transfer extends ITransfer.Stub {

    static Transfer get() {
        return Holder.S_INSTANCE;
    }

    private static final class Holder {
        private static final Transfer S_INSTANCE = new Transfer();
    }

    private Transfer() {
    }

    @Override
    public Reply invoke(String serviceName, StellarMethod stellarMethod) throws RemoteException {
        try {
            Object serviceImpl = RemoteServiceManager.get().getStubService(serviceName);
            Method method = serviceImpl.getClass().getMethod(stellarMethod.getMethodName(),
                    getArgsTypes(stellarMethod.getArgs()));
            Object obj = method.invoke(serviceImpl, stellarMethod.getArgs());
            return Reply.success(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return Reply.failed(e.getClass().getCanonicalName() + ": " + e.getMessage());
        }
    }

    private Class[] getArgsTypes(Object[] args) {
        Class[] argClass = null;
        if (args != null) {
            argClass = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                argClass[i] = stripClass(arg.getClass());
            }
        }
        return argClass;
    }

    private Class stripClass(Class<?> wrapClass) {
        if (wrapClass.isPrimitive()) {
            return wrapClass;
        }
        if (wrapClass == Integer.class) {
            return int.class;
        }
        if (ICallback.class.isAssignableFrom(wrapClass)) {
            Log.d("@@@hua", "还原参数");
            return ICallback.class;
        }
        return wrapClass;
    }
}
