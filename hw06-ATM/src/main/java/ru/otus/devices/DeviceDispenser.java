package ru.otus.devices;

import ru.otus.devices.interfaces.IDeviceDispenser;

import java.util.Map;

public class DeviceDispenser implements IDeviceDispenser {
    @Override
    public void putMoney(Map<Integer, Integer> money) {
        //Устройство выдаёт купюры
    }
}
