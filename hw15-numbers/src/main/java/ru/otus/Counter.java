package ru.otus;

public class Counter {
    private volatile int delta = 1;
    private volatile int val;

    Counter(int initial){
        this.val = initial;
    }

    public void setVal(int val) {
        this.val = val;
        System.out.println(Thread.currentThread().toString() + ": " + String.valueOf(val));
    }

    public Integer getVal() {
        return val;
    }

    public int getDelta() {
        return delta;
    }

    public void reverseDelta() {
        delta = -delta;
    }
}
