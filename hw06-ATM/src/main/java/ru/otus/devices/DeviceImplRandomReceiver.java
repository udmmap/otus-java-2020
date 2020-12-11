package ru.otus.devices;

import ru.otus.devices.interfaces.DeviceReceiver;
import ru.otus.enums.ENominal;

import java.util.*;

public class DeviceImplRandomReceiver implements DeviceReceiver {
    private final Random random = new Random();

    @Override
    public Map<ENominal, Integer> getMoney(){
        //Операции по получению денег из приёмника
        List<ENominal> nominal = new ArrayList(){{
           add(ENominal.N50R);
            add(ENominal.N100R);
            add(ENominal.N200R);
            add(ENominal.N500R);
            add(ENominal.N1000R);
            add(ENominal.N2000R);
            add(ENominal.N5000R);
        }};
        Map<ENominal,Integer> money = new HashMap<ENominal, Integer>();
        money.put(nominal.get(random.nextInt(nominal.size())), random.nextInt(10));
        money.put(nominal.get(random.nextInt(nominal.size())), random.nextInt(10));
        money.put(nominal.get(random.nextInt(nominal.size())), random.nextInt(10));
        money.put(nominal.get(random.nextInt(nominal.size())), random.nextInt(10));
        return money;
    }

    @Override
    public void returnMoney(Map<ENominal,Integer> money) {
    }
}
