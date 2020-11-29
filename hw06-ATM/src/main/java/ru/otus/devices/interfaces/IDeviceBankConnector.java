package ru.otus.devices.interfaces;

public interface IDeviceBankConnector{
    void reserveAmount(int userID, int AccountID, int amount);
    void commitAmount(int userID, int AccountID);
    void rollbackAmount(int userID, int AccountID);
}
