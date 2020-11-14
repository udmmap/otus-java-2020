package ru.otus.ioc;

import ru.otus.action.Action;
import ru.otus.action.IAction;
import ru.otus.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {
    private Ioc() {
    }

    public static IAction createAction() {
        InvocationHandler handler = new PrInvocationHandler(new Action());
        return (IAction) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{IAction.class}, handler);
    }

    static class PrInvocationHandler implements InvocationHandler {
        private final IAction kernObject;

        PrInvocationHandler(IAction kernObject) {
            this.kernObject = kernObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)){
                System.out.println("invoking method:" + method);
                Arrays.stream(args).forEach(o->System.out.print("param:"+o.toString()+" "));
                System.out.println(";");
            }
            return method.invoke(kernObject, args);
        }

        @Override
        public String toString() {
            return "InvocationHandler{" +
                    "Object = " + kernObject +
                    '}';
        }
    }

}
