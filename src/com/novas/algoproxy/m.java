package com.novas.algoproxy;

import java.lang.reflect.Field;

/**
 * Created by novas on 16/6/10.
 */
public class m {
    int c;
    double a;
    public static void main(String[] args)
    {
        AlgoProxy algoProxy=new AlgoProxy("192.168.1.150",9081);
        algoProxy.getAllMethods();
        algoProxy.getMethod("fcm");
    }
}
