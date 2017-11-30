package com.example.aidlcient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myaidlapplication.IImoocAIDL;

/**
 * Created by Administrator on 2017/11/11 0011.
 */

public class MainActivity extends AppCompatActivity {
    private EditText num1;
    private EditText num2;
    private TextView text;
    private Button button;

    private IImoocAIDL iImoocAIDL;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bindService();
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num11 = Integer.parseInt(num1.getText().toString());
                int num22 = Integer.parseInt(num2.getText().toString());
                try {
                    int add = iImoocAIDL.add(num11, num22);
                    text.setText(num11 +"+"+ num22 +"="+ add);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void bindService() {

        Intent intent = new Intent();
        intent.setAction("com.example.administrator.myaidlapplication.IRemoteService");
        //新版本（5.0后）必须显式intent启动 绑定服务
        // 第一个参数是要启动应用的包名称数
        // 第二个是你要启动的Activity或者Service的全称
        intent.setComponent(new ComponentName("com.example.administrator.myaidlapplication", "com.example.administrator.myaidlapplication.IRemoteService"));
        //绑定的时候服务端自动创建
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//绑定服务，回调onBind()方法
            iImoocAIDL = IImoocAIDL.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iImoocAIDL = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
