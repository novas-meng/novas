package com.novas.algoproxy;

/**
 * Created by novas on 16/6/10.
 */
//自定义异常，当参数类型不匹配的时候抛出，例如m为int类型，当赋值为字符串是，抛出异常
public class ParamsTypeException extends Exception
{
   public ParamsTypeException(String msg)
   {
       super(msg);
   }
}
