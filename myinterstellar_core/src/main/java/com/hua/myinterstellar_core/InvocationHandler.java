package com.hua.myinterstellar_core;

import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 10:04
 */

class InvocationHandler implements java.lang.reflect.InvocationHandler {

    private String interfaceName;
    private ITransfer transfer;

    InvocationHandler(String interfaceName, ITransfer transfer) {
        this.interfaceName = interfaceName;
        this.transfer = transfer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StellarMethod stellarMethod = new StellarMethod(method.getName(), args);
        Reply reply = null;
        try {
            reply = transfer.invoke(interfaceName, stellarMethod);
        } catch (RemoteException e) {
            throw new StellarException("调用远程方法 " + method.getName() + " 失败，原因：" + e.getMessage());
        }
        if (reply.getCode() == Reply.REPLY_SUCCEED) {
            // Log.d("@@@hua", "方法调用后，method = "+ stellarMethod);
            return reply.getResult();
        }
        throw new StellarException("调用远程方法 " + method.getName() + " 失败，原因：" + reply.getErrorMsg());
    }
}
