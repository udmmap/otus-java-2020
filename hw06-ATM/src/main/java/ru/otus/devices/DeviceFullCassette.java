package ru.otus.devices;

import ru.otus.devices.interfaces.IDeviceCassette;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeviceFullCassette implements IDeviceCassette {
    private Map<Integer,Integer> money = new TreeMap<Integer,Integer>(Collections.reverseOrder());

    public DeviceFullCassette() {
        money.putAll(
            Map.of(
                50, 10
                , 100, 10
                , 200, 10
                , 500, 10
                , 1000, 10
                , 2000, 10
                , 5000, 10
            ));
    }

    @Override
    public Map<Integer, Integer> getMoney(int amount) throws RuntimeException {
        Map<Integer, Integer> res = new TreeMap<Integer, Integer>();
        int remains=amount;
        Iterator<Map.Entry<Integer,Integer>> it = money.entrySet().iterator();
        while (remains>0 && it.hasNext()){
            Map.Entry<Integer,Integer> e = it.next();
            int dif = Math.min(remains / e.getKey(), e.getValue());
            if (dif>0) {
                remains -= dif * e.getKey();
                res.putIfAbsent(e.getKey(), dif);
            }
        }
        if (remains==0){
            res.forEach(
                    (key, value) -> this.money.merge(key, value, (v1, v2) -> v1-v2));
        } else {
            throw new RuntimeException("Сумма отсутствует в банкомате");
        }

        return res;
    }

    @Override
    public void putMoney(Map<Integer, Integer> money) {
        Map<Integer, Integer> res = Stream
                .concat(this.money.entrySet().stream(), money.entrySet().stream())
                .collect(
                        Collectors.groupingBy(Map.Entry::getKey
                                ,Collectors.summingInt(Map.Entry::getValue)));
        this.money = res;
    }
}
