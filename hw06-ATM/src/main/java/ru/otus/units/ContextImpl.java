package ru.otus.units;

import ru.otus.enums.ENominal;
import ru.otus.enums.EOperationType;

import java.util.Map;
import java.util.TreeMap;

public class ContextImpl implements Context {
    private final EOperationType operationType;
    private final int userId;
    private final int accountId;
    private int amount;
    private Map<ENominal,Integer> money;

    private StringBuilder sbLog = new StringBuilder();

    public ContextImpl(EOperationType operationType, int userId, int accountId)
    {
        this.operationType = operationType;
        this.userId = userId;
        this.accountId = accountId;
    }

    public ContextImpl(EOperationType operationType, int userId, int accountId, int amount)
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
    public void setMoney(Map<ENominal,Integer> money) throws RuntimeException {
        this.money = new TreeMap(money);
        this.amount = money.entrySet().stream()
                .map((e) -> {
                    return e.getKey().toInt() * e.getValue();
                }).mapToInt(Integer::intValue).sum();
    }

    @Override
    public Map<ENominal, Integer> getMoney() {
        return new TreeMap<ENominal,Integer>(this.money);
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
