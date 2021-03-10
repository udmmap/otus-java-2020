package ru.otus;

public class Counter {
    private int delta = 1;
    private int val;

    Counter(int initial){
        this.val = initial;
    }

    public void setVal(int val) {
        this.val = val;
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
