package ru.otus;

import ru.otus.action.Action;
import ru.otus.action.IAction;
import ru.otus.ioc.Ioc;

import java.util.ArrayList;

public class RunProxyLogger {
    public static void main(String... args) {
        IAction proxyAction = Ioc.createAction();
        System.out.println(proxyAction.calculate(555)+"\n");
        System.out.println(proxyAction.calculateNoLog("Not Logged")+"\n");
        System.out.println(proxyAction.calculateOwnLog("Аннотации имплементации не видны")+"\n");
        proxyAction.calculation(0,1,"Line");
        System.out.println("-------------------------------------------------");
    }
}