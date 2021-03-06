package org.jim.dynproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Java Proxy
 * - 角色：接口，真实对象，代理对象
 * - 创建代理对象：Proxy#newProxyInstance(ClassLoader, Class<?>[], InvocationHandler)
 * - 代理对象执行 InvocationHandler#invoke(Object, Method, Object[])
 */
public class CarInvocationHandler implements InvocationHandler {

    private Car car;

    public CarInvocationHandler(Car car) {
        this.car = car;
    }

    /**
     * 动态调用
     *
     * @param proxy 动态生成的代理对象
     * @param method 接口的方法
     * @param args 方法的参数
     * @return 方法的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("before");

        // 创建代理对象
//        Proxy.newProxyInstance(
//                ClassLoader,
//                Class<?>[], // interface
//                InvocationHandler
//        );

        // 执行目标对象的方法
        method.invoke(car, args);

        System.err.println("after");
        return null;
    }

}
