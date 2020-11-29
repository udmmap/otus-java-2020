package ru.otus.units;

import ru.otus.devices.interfaces.IDeviceDispenser;

public class UnitDispenser extends Unit {
    IDeviceDispenser device;
    public UnitDispenser(IDeviceDispenser device){
        this.device = device;
    }

    @Override
    public void Process(ITransaction t) {
        device.putMoney(t.getMoney());

        super.Process(t);
    }
}
