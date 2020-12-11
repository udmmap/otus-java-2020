package ru.otus.units;

import ru.otus.devices.interfaces.DeviceDispenser;

public class UnitDispenser extends UnitImpl {
    DeviceDispenser device;
    public UnitDispenser(DeviceDispenser device){
        this.device = device;
    }

    @Override
    public void Process(Context t) {
        device.putMoney(t.getMoney());

        super.Process(t);
    }
}
