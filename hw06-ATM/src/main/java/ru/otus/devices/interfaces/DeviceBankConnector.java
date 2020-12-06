package ru.otus.devices.interfaces;

public interface DeviceBankConnector {
    void reserveAmount(int userID, int AccountID, int amount);
    void commitAmount(int userID, int AccountID);
    void rollbackAmount(int userID, int AccountID);
}
