package ru.otus.action;

import ru.otus.annotation.Log;

public class Action implements IAction{
    @Override
    public int calculate(int arg) {
        return arg;
    }

    @Override
    public String calculateNoLog(String arg) {
        return arg;
    }

    @Log
    @Override
    public String calculateOwnLog(String arg) {
        return arg;
    }

    @Override
    public void calculation(int arg1, int arg2, String arg3) {
        System.out.println(arg3+"---"+arg1+"---"+arg2);
    }
}
