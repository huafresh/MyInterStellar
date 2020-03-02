package com.hua.myinterstellar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
