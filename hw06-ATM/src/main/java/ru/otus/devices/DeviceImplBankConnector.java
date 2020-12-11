package ru.otus.devices;

public class DeviceImplBankConnector implements ru.otus.devices.interfaces.DeviceBankConnector {
    @Override
    public void reserveAmount(int userID, int AccountID, int amount) {
        if (amount>0) {
            System.out.println("Счёт готов к зачислению.");
        } else {
            System.out.println("Сумма списания зарезервирована на счету.");
        }
    }

    @Override
    public void commitAmount(int userID, int AccountID) {
        System.out.println("Транзакция подтверждена.");
    }

    @Override
    public void rollbackAmount(int userID, int AccountID) {
        System.out.println("Транзакция отменена.");
    }

}
