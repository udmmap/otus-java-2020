package ru.otus.devices.interfaces;

import java.util.Map;

public interface IDeviceCassette {
    Map<Integer, Integer> getMoney(int amount);
    void putMoney(Map<Integer,Integer> money);
}
