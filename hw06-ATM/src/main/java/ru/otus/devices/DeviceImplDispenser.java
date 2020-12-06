package ru.otus.devices;

import ru.otus.enums.ENominal;

import java.util.Map;

public class DeviceImplDispenser implements ru.otus.devices.interfaces.DeviceDispenser {
    @Override
    public void putMoney(Map<ENominal, Integer> money) {
        System.out.print("Выдано: ");
        money.forEach((e,v)->{
            System.out.print(String.format("%d купюр номиналом %s. ",v,e.toString()));
        });
        System.out.println();
    }
}
