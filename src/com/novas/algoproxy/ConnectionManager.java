package com.novas.algoproxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.sql.Connection;

import static java.net.InetAddress.*;

/**
 * Created by novas on 16/6/10.
 */
//这个类负责进行消息的发送和接收
public class ConnectionManager {
    private static ConnectionManager connectionManager=null;
    //这个socket是从属性服务获取参数用的
    private Socket confSocket=null;
    //这个socket是向运行socket传递参数
    private Socket runSocket=null;
    private String serverIp=null;
    private int port;
    private ConnectionManager(String serverIp,int port)
    {
        confSocket=new Socket();
        runSocket=new Socket();
        this.serverIp=serverIp;
        this.port=port;
    }
    //Socket端传递过来数据格式2_99_参数
//2表示参数长度的位数，比如99是两位数，99表示参数长度，参数协议为为PARAMS_1或者PARAMS_2_fcm
    public  byte[] getSocketParams(InputStream is) throws IOException {
        int W=is.read()-48;
        byte[] paramsLengthBytes=new byte[W+2];
        is.read(paramsLengthBytes);
        int length=Integer.parseInt(new String(paramsLengthBytes,1,W));
//保存最后的结果
        byte[] bytes=new byte[length];
//
        byte[] tempbytes=new byte[1024];
        int nowLength=0;
        while (nowLength!=length)
        {
            int readLength=is.read(tempbytes);
            System.arraycopy(tempbytes,0,bytes,nowLength,readLength);
            nowLength=nowLength+readLength;
        }
        return bytes;
    }
    public synchronized static ConnectionManager getConnectionManagerInstance(String serverIp,int port)
    {
        if(connectionManager==null)
        {
            connectionManager=new ConnectionManager(serverIp,port);
        }
        return connectionManager;
    }
    public byte[] sendRun(String msg)
    {
       return sendMsg(msg,runSocket);
    }
    public byte[] sendConf(String msg)
    {
        return sendMsg(msg,confSocket);
    }
    //发送消息,返回接收到的结果数据
    public byte[] sendMsg(String msg,Socket socket)
    {
        byte[] resultBytes=null;
        try {
            SocketAddress address=new InetSocketAddress(InetAddress.getByName(serverIp),port);
            socket.connect(address);
            System.out.println("msg=" + msg);
            String length=msg.length()+"";
            String request=length.length()+"_"+length+"_"+msg;
            System.out.println(request);
            byte[] bytes=request.getBytes();
            System.out.println(bytes[0]);
            socket.getOutputStream().write(bytes);
            resultBytes=getSocketParams(socket.getInputStream());
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultBytes;
    }
}
