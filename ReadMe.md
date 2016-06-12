这部分是客户端远程调用底层方法的实现，下面具体介绍如何使用；
首先生成AlgoProxy对象，根据这个对象调用远端方法，目前提供三种方法调用，分别是返回远端支持的所有的算法，根据算法名称返程算法调用，返回远端支持的所有算法的个数
如果需要增加其余方法调用，请联系我。
        AlgoProxy algoProxy=new AlgoProxy("192.168.1.150",9087,9081);
192.168.1.150是远端ip，9087是远端运行服务的端口，9081是远端属性服务的端口；
获取完AlgoProxy对象以后
1：返回远端支持的所有算法
        MethodProxy[] methodProxies=algoProxy.getAllMethods();
算法具体为一个MethodProxy对象，这个对象里面包含了算法名称和算法需要的参数和对应的参数类型
2：根据算法名称获取MethodProxy对象
        MethodProxy proxy=algoProxy.getMethod("fcm");
3：调用方法
  //以下为具体调用方法
        //1:设置参数
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("m",4);
        hashMap.put("c",5);
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

   上述是具体调用例子

 以下给出一个正确用例和错误用例
        正确用例
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
                hashMap.put("m",4);
                hashMap.put("c",5);
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
           运行结果

1
fcm
c
java.lang.Integer
m
java.lang.Integer
run=ACCEPTOK


错误用例

  //以下为具体调用方法
        //1:设置参数
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("m",4);
        hashMap.put("c","a");
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

       运行结果，从运行结果可以看出，参数类型错误，因为c是int类型，但是却赋值为string
    com.novas.algoproxy.ParamsTypeException: 此参数类型类型
    	at com.novas.algoproxy.MethodProxy.setParamsToValue(MethodProxy.java:39)
    	at com.novas.algoproxy.m.main(m.java:34)
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    	at java.lang.reflect.Method.invoke(Method.java:606)
    	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)

  有问题请联系我