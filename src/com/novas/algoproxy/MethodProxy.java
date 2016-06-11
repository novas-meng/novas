package com.novas.algoproxy;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by novas on 16/6/10.
 */
public class MethodProxy
{

    private String methodName;
    //参数和type对应的hashmap
    private HashMap<String,Class> paramsToType=new HashMap<>();
    //存储map对应的参数的字符串表示，形式为[{m,2},{c,3}] 其中2 3为具体数值;
    private StringBuilder stringBuilder=new StringBuilder();
    //设置要传输的参数，同时进行参数的类型校对，如果类型错误，抛出错误
    public void setParamsToValue(HashMap<String,Object> paramsValue) throws ParamsNameException, ParamsTypeException {
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
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("]");
    }
    //解析相应的字符串形式，字符串形式为{fcm,[{m,2},{c,2}]};
    public MethodProxy(String params)
    {
        int index=params.indexOf(',');
        methodName=params.substring(1,index);
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
            if(var2.equals("1"))
            {
                paramsToType.put(var1,Constants.StringType);
            }
            //2表示参数类型为Integer
            else if(var2.equals("2"))
            {
                paramsToType.put(var1,Constants.IntegerType);
            }
            //3表示参数类型为Double
            else if(var2.equals("3"))
            {
                paramsToType.put(var1,Constants.DoubleType);
            }
        }
    }

}
