package ru.otus.devices.interfaces;

import ru.otus.enums.ENominal;

import java.util.Map;

public interface DeviceCassette {
    Map<ENominal, Integer> getMoney(int amount);
    void putMoney(Map<ENominal,Integer> money);
}
