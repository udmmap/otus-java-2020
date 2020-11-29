package ru.otus.devices.interfaces;

import java.util.Map;

public interface IDeviceReceiver {
    Map<Integer, Integer> getMoney();
    void returnMoney(Map<Integer, Integer> money);
}
