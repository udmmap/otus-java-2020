package ru.otus.units;

public interface Unit {
    Unit setPrevUnit(Unit pu);
    Unit setNextUnit(Unit nu);
    void Process(Context t);
    void onCommit(Context t);
    void onRollback(Context t);
}
