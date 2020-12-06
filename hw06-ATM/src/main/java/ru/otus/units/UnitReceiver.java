package ru.otus.units;

import ru.otus.devices.interfaces.DeviceReceiver;
import ru.otus.enums.ENominal;

import java.util.HashMap;

public class UnitReceiver extends UnitImpl {

    private final DeviceReceiver device;

    public UnitReceiver(DeviceReceiver device){
        this.device = device;
    }

    @Override
    public void Process(Context cx) {
        cx.setMoney(device.getMoney());
        super.Process(cx);
    }

    @Override
    public void onRollback(Context cx) {
        device.returnMoney(cx.getMoney());
        cx.Log("Купюры возвращены пользователю!!!\n");
        cx.setMoney(new HashMap<ENominal,Integer>());
        super.onRollback(cx);
    }
}
