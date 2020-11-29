package ru.otus.units;

import ru.otus.devices.interfaces.IDeviceCassette;

public class UnitCassette extends Unit {
    private IDeviceCassette device;
    public UnitCassette(IDeviceCassette device){
        this.device = device;
    }
    @Override
    public void Process(ITransaction t) {
        try {
            switch(t.getOperationType()) {
                case AMOUNT_RECEIVING -> device.putMoney(t.getMoney());
                case AMOUNT_DISPENSING -> t.setMoney(device.getMoney(t.getAmount()));
            }

            super.Process(t);
        } catch (Exception e) {
            t.Log(e.toString());
            this.onRollback(t);
        }
    }
}
