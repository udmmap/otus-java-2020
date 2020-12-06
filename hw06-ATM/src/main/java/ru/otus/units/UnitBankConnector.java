package ru.otus.units;

import ru.otus.devices.interfaces.DeviceBankConnector;

public class UnitBankConnector extends UnitImpl {
    final private DeviceBankConnector device;

    public UnitBankConnector(DeviceBankConnector device){
        this.device = device;
    }

    @Override
    public void Process(Context t) {
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
    public void onCommit(Context t) {
        device.commitAmount(t.getUserID(), t.getAccountID());
        super.onCommit(t);
    }

    @Override
    public void onRollback(Context t) {
        device.rollbackAmount(t.getUserID(), t.getAccountID());
        super.onRollback(t);
    }
}
