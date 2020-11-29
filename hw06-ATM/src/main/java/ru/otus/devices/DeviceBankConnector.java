package ru.otus.devices;

import ru.otus.devices.interfaces.IDeviceBankConnector;

public class DeviceBankConnector implements IDeviceBankConnector {
    @Override
    public void reserveAmount(int userID, int AccountID, int amount) {
        //резервирование суммы на счёте
    }

    @Override
    public void commitAmount(int userID, int AccountID) {
        //подтверждение действия со счетом
    }

    @Override
    public void rollbackAmount(int userID, int AccountID) {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
