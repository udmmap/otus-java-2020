package ru.otus.units;

public interface IUnit {
    IUnit setPrevUnit(IUnit pu);
    IUnit setNextUnit(IUnit nu);
    void Process(ITransaction t);
    void onCommit(ITransaction t);
    void onRollback(ITransaction t);
}
