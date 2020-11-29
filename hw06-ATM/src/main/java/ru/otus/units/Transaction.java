package ru.otus.units;

import ru.otus.enums.EOperationType;

import java.util.Map;
import java.util.TreeMap;

public class Transaction implements ITransaction {
    final EOperationType operationType;
    final int userId;
    final int accountId;
    int amount;
    Map<Integer,Integer> money;

    StringBuilder sbLog = new StringBuilder();

    public Transaction(EOperationType operationType, int userId, int accountId)
    {
        this.operationType = operationType;
        this.userId = userId;
        this.accountId = accountId;
    }

    public Transaction(EOperationType operationType, int userId, int accountId, int amount)
    {
        this.operationType = operationType;
        this.userId = userId;
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public EOperationType getOperationType() {
        return operationType;
    }

    @Override
    public int getUserID() {
        return userId;
    }

    @Override
    public int getAccountID() {
        return accountId;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setMoney(Map<Integer,Integer> money) throws RuntimeException {
        this.money = new TreeMap(money);
        this.amount = money.entrySet().stream()
                .map((e) -> {
                    return e.getKey() * e.getValue();
                }).mapToInt(Integer::intValue).sum();
    }

    @Override
    public Map<Integer, Integer> getMoney() {
        return new TreeMap<Integer,Integer>(this.money);
    }

    @Override
    public void Log(String line) {
        this.sbLog.append(line);
    }

    @Override
    public String getLog() {
        return this.sbLog.toString();
    }
}
