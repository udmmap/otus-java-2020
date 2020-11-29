package ru.otus.devices;

import ru.otus.devices.interfaces.IDeviceReceiver;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DeviceRandomReceiver implements IDeviceReceiver {
    private final Random random = new Random();

    @Override
    public Map<Integer, Integer> getMoney(){
        //Операции по получению денег из приёмника
        int[] nominal = new int[]{50,100,200,500,1000,2000,5000};
        Map<Integer,Integer> money = new HashMap<Integer, Integer>();
        money.put(nominal[random.nextInt(nominal.length)], random.nextInt(10));
        money.put(nominal[random.nextInt(nominal.length)], random.nextInt(10));
        money.put(nominal[random.nextInt(nominal.length)], random.nextInt(10));
        money.put(nominal[random.nextInt(nominal.length)], random.nextInt(10));
        return money;
    }

    @Override
    public void returnMoney(Map<Integer,Integer> money) {
    }
}
