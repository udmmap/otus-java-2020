package ru.otus.units;

import ru.otus.devices.interfaces.IDeviceBankConnector;

public class UnitBankConnector extends Unit{
    final private IDeviceBankConnector device;

    public UnitBankConnector(IDeviceBankConnector device){
        this.device = device;
    }

    @Override
    public void Process(ITransaction t) {
        try {
            switch (t.getOperationType()) {
                case AMOUNT_RECEIVING -> device.reserveAmount(t.getUserID(), t.getAccountID(), t.getAmount());
                case AMOUNT_DISPENSING -> device.reserveAmount(t.getUserID(), t.getAccountID(), -t.getAmount());
            }
            super.Process(t);
        } catch (Exception e) {
            t.Log(e.toString()+"\n");
            this.onRollback(t);
        }
    }

    @Override
    public void onCommit(ITransaction t) {
        device.commitAmount(t.getUserID(), t.getAccountID());
        super.onCommit(t);
    }

    @Override
    public void onRollback(ITransaction t) {
        device.rollbackAmount(t.getUserID(), t.getAccountID());
        super.onRollback(t);
    }
}
