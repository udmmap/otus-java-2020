package ru.otus.devices.interfaces;

import ru.otus.enums.ENominal;

import java.util.Map;

public interface DeviceReceiver {
    Map<ENominal, Integer> getMoney();
    void returnMoney(Map<ENominal, Integer> money);
}
