package com.novas.algoproxy;

/**
 * Created by novas on 16/6/10.
 */
public interface proxy {
    public MethodProxy[] getAllMethods();
    public MethodProxy getMethod(String name);
    public long callMethod(MethodProxy methodProxy);
}
