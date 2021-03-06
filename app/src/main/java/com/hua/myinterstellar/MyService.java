package com.hua.myinterstellar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.hua.myinterstellar_core.ICallback;
import com.hua.myinterstellar_core.ITestOneWay;
import com.hua.myinterstellar_core.InterStellar;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 11:10
 */

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            InterStellar.registerRemoteService(IApi.class, new IApi() {
                @Override
                public int testBasicType(int a, int b) {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return a + b;
                }

                @Override
                public String testParcelable(ManInfo manInfo) {
                    return manInfo.getName() + ":" + manInfo.getAge();
                }

                @Override
                public void testInOut(ManInfo manInfo) {
                    manInfo.setName(manInfo.getName() + " append lisi");
                    manInfo.setAge(manInfo.getAge() + 20);
                }

                @Override
                public void testCallback(ICallback callback) {
                    try {
                        callback.onFail("哦豁， 失败了");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ITestOneWay.Stub() {
            @Override
            public void invoke(String name) throws RemoteException {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("@@@hua", "test one way: " + name);
            }
        };
    }

}
