package ru.otus.action;

import ru.otus.annotation.Log;

public interface IAction {
    @Log
    public int calculate(int arg);

    public String calculateNoLog(String arg);

    public String  calculateOwnLog(String arg);

    @Log
    public void calculation(int arg1, int arg2, String arg3);
}
