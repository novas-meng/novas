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
    public AlgoProxy(String serverIp,int runport,int confport)
    {
        connectionManager=ConnectionManager.getConnectionManagerInstance(serverIp,runport,confport);
    }
    @Override
    public MethodProxy[] getAllMethods() {
        byte[] bytes=connectionManager.sendConf(getAllMsg);
        String response=new String(bytes);
        MethodProxy[] methodProxies=null;
        if(response.charAt(0)=='['&&response.charAt(response.length()-1)==']')
        {
            response=response.substring(1,response.length()-1);
            String[] args=response.split("-");
            methodProxies=new MethodProxy[args.length];
            for(int i=0;i<args.length;i++)
            {
                MethodProxy methodProxy=new MethodProxy(args[i]);
                methodProxies[i]=methodProxy;
            }
        }
      //  System.out.println("recvmsg="+new String(bytes));
        return methodProxies;
    }

    @Override
    public MethodProxy getMethod(String name) {
        byte[] bytes=connectionManager.sendConf("{2,"+name+"}");
        String response=new String(bytes);
        MethodProxy methodProxy=null;
        if(response.charAt(0)=='{'&&response.charAt(response.length()-1)=='}')
        {
          //  System.out.println("response="+response);
            methodProxy=new MethodProxy(response);
        }
        return methodProxy;
    }

    @Override
    public long callMethod(MethodProxy methodProxy) {
        if(methodProxy.isParamsSet())
        {
            String request=methodProxy.toRequestString();
            byte[] res=connectionManager.sendRun(request);
            if(new String(res).equals("ACCEPTOK"))
            {
                System.out.println("run="+new String(res));
                return methodProxy.getTimeStamp();
            }
        }
        return -1;
    }
}
