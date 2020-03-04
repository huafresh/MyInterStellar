package com.hua.myinterstellar_core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-03 11:09
 */

public abstract class BaseCallback extends ICallback.Stub {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onSuccess(final Bundle result) throws RemoteException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSucceed(result);
            }
        });
    }

    @Override
    public void onFail(final String errorMsg) throws RemoteException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(errorMsg);
            }
        });
    }

    public abstract void onSucceed(Bundle result);

    public abstract void onFailed(String errorMsg);
}
