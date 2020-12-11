package ru.otus.devices;

import ru.otus.devices.interfaces.DeviceBankConnector;

public class DeviceImplAngryBankConnector implements DeviceBankConnector {
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
