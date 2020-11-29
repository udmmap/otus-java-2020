package ru.otus.units;

import ru.otus.devices.interfaces.IDeviceReceiver;

import java.util.HashMap;

public class UnitReceiver extends Unit{

    private final IDeviceReceiver device;

    public UnitReceiver(IDeviceReceiver device){
        this.device = device;
    }

    @Override
    public void Process(ITransaction t) {
        t.setMoney(device.getMoney());
        super.Process(t);
    }

    @Override
    public void onRollback(ITransaction t) {
        device.returnMoney(t.getMoney());
        t.Log("Купюры возвращены пользователю!!!\n");
        t.setMoney(new HashMap<Integer,Integer>());
        super.onRollback(t);
    }
}
