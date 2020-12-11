package ru.otus.units;

import ru.otus.devices.interfaces.DeviceCassette;

public class UnitCassette extends UnitImpl {
    private DeviceCassette device;
    public UnitCassette(DeviceCassette device){
        this.device = device;
    }
    @Override
    public void Process(Context t) {
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
