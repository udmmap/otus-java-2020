package ru.otus.units;

import ru.otus.enums.EOperationType;

import java.util.Map;

public interface ITransaction {
    EOperationType getOperationType();
    int getUserID();
    int getAccountID();
    int getAmount();
    void setAmount(int amount);
    void setMoney(Map<Integer,Integer> money);
    Map<Integer,Integer> getMoney();
    void Log(String line);
    String getLog();
}
