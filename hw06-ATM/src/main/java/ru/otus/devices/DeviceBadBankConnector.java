package ru.otus.devices;

import ru.otus.devices.interfaces.IDeviceBankConnector;

public class DeviceBadBankConnector implements IDeviceBankConnector {
    @Override
    public void reserveAmount(int userID, int AccountID, int amount) {
        throw new RuntimeException("Счёт заблокирован!!!");
    }

    @Override
    public void commitAmount(int userID, int AccountID) {

    }

    @Override
    public void rollbackAmount(int userID, int AccountID) {

    }
}
