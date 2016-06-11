package com.novas.algoproxy;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * Created by novas on 16/6/10.
 */
public class AlgoProxy implements proxy{

    ConnectionManager connectionManager=null;
    final String getAllMsg="{1}";
    public AlgoProxy(String serverIp,int port)
    {
        connectionManager=ConnectionManager.getConnectionManagerInstance(serverIp,port);
    }
    @Override
    public MethodProxy[] getAllMethods() {
        byte[] bytes=connectionManager.sendConf("{1}");
        String response=new String(bytes);
        ArrayList
        if(response.charAt(0)=='['&&response.charAt(response.length()-1)==']')
        {
            response=response.substring(1,response.length()-1);
        }
        System.out.println("recvmsg="+new String(bytes));
        return new MethodProxy[0];
    }

    @Override
    public MethodProxy getMethod(String name) {
        return null;
    }

    @Override
    public boolean callMethod(MethodProxy methodProxy) {
        return false;
    }
}
