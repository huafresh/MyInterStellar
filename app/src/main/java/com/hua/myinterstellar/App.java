package com.hua.myinterstellar;

import android.app.Application;

import com.hua.myinterstellar_core.InterStellar;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 11:11
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InterStellar.init(this);
    }
}
