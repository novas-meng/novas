package com.novas.algoproxy;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16/6/10.
 */
public class MethodProxy
{

    private int timestamp;
    private String methodName;
    //参数和type对应的hashmap
    private HashMap<String,Class> paramsToType=new HashMap<>();
    //存储map对应的参数的字符串表示，形式为[{m,2},{c,3}] 其中2 3为具体数值;
    private StringBuilder stringBuilder=new StringBuilder();
    //
    private StringBuilder requestBuilder=new StringBuilder();
    private int paramsSet=0;
    //设置要传输的参数，同时进行参数的类型校对，如果类型错误，抛出错误
    public void setParamsToValue(HashMap<String,Object> paramsValue) throws ParamsNameException, ParamsTypeException {
        paramsSet=1;
        stringBuilder.append("[");
        for(Map.Entry<String,Object> entry:paramsValue.entrySet())
        {
            String name=entry.getKey();
            Class type=paramsToType.get(name);
            if(type==null)
            {
                throw new ParamsNameException("没有此参数");
            }
            Object value=paramsValue.get(name);
            if(value.getClass()!=type)
            {
                throw new ParamsTypeException("此参数类型类型");
            }
            String var1=null;
            if(type==Integer.class)
            {
                var1=(Integer)value+"";
            }
            else if(type==String.class)
            {
                var1=(String)value;
            }
            else if(type==Double.class)
            {
                var1 = (Double) value + "";
            }
            stringBuilder.append("{"+name+","+var1+"},");
            requestBuilder.append("{"+name+"="+var1+"}");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("]");
    }
    //返回时间戳
    public long getTimeStamp()
    {
        return timestamp;
    }
    //判断是否设置参数
    public boolean isParamsSet()
    {
        return paramsSet==1?true:false;
    }
    //解析成调用方法所需的字符串形式比如234 fcm {m=2}{c=3}
    public String toRequestString()
    {
        Calendar calendar=Calendar.getInstance();
        timestamp=(int)calendar.getTime().getTime();

        return timestamp+" "+methodName+" "+requestBuilder.toString();
    }
    public HashMap<String,Class> getParamsToType()
    {
        return this.paramsToType;
    }
    public String getMethodName()
    {
        return this.methodName;
    }
    //解析相应的字符串形式，字符串形式为{fcm,[{m,2},{c,2}]};
    public MethodProxy(String params)
    {
        int index=params.indexOf(',');
        methodName=params.substring(1,index);
      //  System.out.println(methodName);
        int arrayBeginIndex=params.indexOf('[');
        int arrayEndIndex=params.indexOf(']');
        String array=params.substring(arrayBeginIndex+1,arrayEndIndex);
        arrayEndIndex=-1;
        while ((arrayBeginIndex=array.indexOf('{',arrayEndIndex))!=-1)
        {
            arrayEndIndex=array.indexOf('}',arrayBeginIndex);
            String var1=array.substring(arrayBeginIndex+1,arrayEndIndex);
            String[] var2= var1.split(",");
            //1表示参数类型为String
            if(var2[1].equals("1"))
            {
                paramsToType.put(var2[0],Constants.StringType);
            }
            //2表示参数类型为Integer
            else if(var2[1].equals("2"))
            {
                paramsToType.put(var2[0],Constants.IntegerType);
            }
            //3表示参数类型为Double
            else if(var2[1].equals("3"))
            {
                paramsToType.put(var2[0],Constants.DoubleType);
            }
        }
    }

}
