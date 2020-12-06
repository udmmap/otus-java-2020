package ru.otus.units;

import ru.otus.enums.ENominal;
import ru.otus.enums.EOperationType;

import java.util.Map;

public interface Context {
    EOperationType getOperationType();
    int getUserID();
    int getAccountID();
    int getAmount();
    void setAmount(int amount);
    void setMoney(Map<ENominal,Integer> money);
    Map<ENominal,Integer> getMoney();
    void Log(String line);
    String getLog();
}
