package com.hua.myinterstellar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.MailTo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hua.myinterstellar_core.BaseCallback;
import com.hua.myinterstellar_core.ICallback;
import com.hua.myinterstellar_core.InterStellar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        });

        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IApi api = InterStellar.getRemoteService(IApi.class);
                int add = api.testBasicType(2, 3);
                Log.d("@@@hua", "add = " + add);
                String s = api.testParcelable(new ManInfo("wangwu", 12));
                Log.d("@@@hua", "test parcelable = " + s);
                ManInfo info = new ManInfo("zhangsan", 10);
                api.testInOut(info);
                Log.d("@@@hua", "inout: " + info);

                api.testCallback(new BaseCallback() {
                    @Override
                    public void onSucceed(Bundle result) {

                    }

                    @Override
                    public void onFailed(String errorMsg) {
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        boolean b1 = Integer.class.isPrimitive();
        boolean b2 = int.class.isPrimitive();
        boolean b3 = MailTo.class.isPrimitive();
        Log.d("@@@hua", "b1 = " + b1);
        Log.d("@@@hua", "b2 = " + b2);
        Log.d("@@@hua", "b3 = " + b3);



    }

}
