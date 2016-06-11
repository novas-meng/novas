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
        int m=9;
        Object obj=m;
        if(obj.getClass()==Integer.class)
        {
            System.out.println("fadsf");
        }
        Field[] fields=m.class.getDeclaredFields();
        for(int i=0;i<fields.length;i++)
        {
            // System.out.println(fields[i].getType());
            if(fields[i].getType()==Integer.class)
            {
                System.out.println("afaf");
            }
        }
    }
}
