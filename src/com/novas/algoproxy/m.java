package com.novas.algoproxy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16/6/10.
 */
public class m {
    int c;
    double a;
    public static void main(String[] args)
    {
        //double c=1466681732952;


        AlgoProxy algoProxy=new AlgoProxy("192.168.1.150",9087,9081);
        MethodProxy[] methodProxies=algoProxy.getAllMethods();
        System.out.println(methodProxies.length);
        System.out.println(methodProxies[0].getMethodName());
        HashMap<String,Class> params=algoProxy.getMethod("fcm").getParamsToType();
        for(Map.Entry<String,Class> entry:params.entrySet())
        {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getName());
        }
        //以下为具体调用方法
        //1:设置参数
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("m",3);
        hashMap.put("c",2);
        hashMap.put("inputPath","/home/novas/data");
        hashMap.put("outputPath","/home/novas/martix");
        //2：获取方法MethodProxy对象
        MethodProxy proxy=algoProxy.getMethod("fcm");
        //3:将参数赋值给MethodProxy
        try {
            proxy.setParamsToValue(hashMap);
            //4:调用方法
            algoProxy.callMethod(proxy);
        } catch (ParamsNameException e) {
            e.printStackTrace();
        } catch (ParamsTypeException e) {
            e.printStackTrace();
        }

    }
}
