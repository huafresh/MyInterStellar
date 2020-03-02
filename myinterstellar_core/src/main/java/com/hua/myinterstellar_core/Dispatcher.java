package com.hua.myinterstellar_core;

import android.os.IBinder;
import android.os.RemoteException;

import java.util.HashMap;

/**
 * Dispatcher服务，管理所有其他进程的Transfer Binder对象。
 *
 * @author zhangsh
 * @version V1.0
 * @date 2020-02-29 22:27
 */

class Dispatcher extends IDispatcher.Stub {

    private HashMap<String, String> serviceCache = new HashMap<>();
    private HashMap<String, BinderBean> transferCache = new HashMap<>();

    static Dispatcher get() {
        return Holder.S_INSTANCE;
    }

    private static final class Holder {
        private static final Dispatcher S_INSTANCE = new Dispatcher();
    }

    private Dispatcher() {
    }

    @Override
    public void registerRemoteService(String name, final BinderBean transferInfo) throws RemoteException {
        final IBinder transfer = transferInfo.getTransfer();
        final String processName = transferInfo.getProcessName();
        try {
            transferCache.put(processName, transferInfo);
            serviceCache.put(name, processName);
            transfer.linkToDeath(new DeathRecipient() {
                @Override
                public void binderDied() {
                    transferCache.remove(processName);
                }
            }, 0);
        } catch (RemoteException e) {
            transferCache.remove(processName);
            serviceCache.remove(name);
            throw e;
        }
    }

    @Override
    public void unRegisterRemoteService(String name) throws RemoteException {
        serviceCache.remove(name);
    }

    @Override
    public BinderBean getTransferInfo(String name) throws RemoteException {
        String processName = serviceCache.get(name);
        if (processName != null) {
            return transferCache.get(processName);
        }
        return null;
    }
}
