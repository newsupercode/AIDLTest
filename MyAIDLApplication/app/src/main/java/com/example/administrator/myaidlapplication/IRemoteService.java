package com.example.administrator.myaidlapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Administrator on 2017/11/11 0011.
 * 用来监听客户端的连接请求。
 */

public class IRemoteService extends Service {

    //客户端绑定service时会执行
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IImoocAIDL.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e("TAG","收到了来自客户端的请求" + num1 + "+" + num2 );
            return num1 + num2;
        }
    };
}