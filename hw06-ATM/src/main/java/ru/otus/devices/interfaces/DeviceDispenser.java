package ru.otus.devices.interfaces;

import ru.otus.enums.ENominal;

import java.util.Map;

public interface DeviceDispenser {
    void putMoney(Map<ENominal,Integer> money);
}
