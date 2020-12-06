package ru.otus.devices;

import ru.otus.devices.interfaces.DeviceCassette;
import ru.otus.enums.ENominal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeviceImplFullCassette implements DeviceCassette {
    private Map<ENominal,Integer> money = new TreeMap<ENominal,Integer>(Collections.reverseOrder());

    public DeviceImplFullCassette() {
        money.putAll(
            Map.of(
                    ENominal.N50R, 10
                , ENominal.N100R, 10
                , ENominal.N200R, 10
                , ENominal.N500R, 10
                , ENominal.N1000R, 10
                , ENominal.N2000R, 10
                , ENominal.N5000R, 10
            ));
    }

    @Override
    public Map<ENominal, Integer> getMoney(int amount) throws RuntimeException {
        Map<ENominal, Integer> res = new TreeMap<ENominal, Integer>();
        int remains=amount;
        Iterator<Map.Entry<ENominal,Integer>> it = money.entrySet().iterator();
        while (remains>0 && it.hasNext()){
            Map.Entry<ENominal,Integer> e = it.next();
            int dif = Math.min(remains / e.getKey().toInt(), e.getValue());
            if (dif>0) {
                remains -= dif * e.getKey().toInt();
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
    public void putMoney(Map<ENominal, Integer> money) {
        Map<ENominal, Integer> res = Stream
                .concat(this.money.entrySet().stream(), money.entrySet().stream())
                .collect(
                        Collectors.groupingBy(Map.Entry::getKey
                                ,Collectors.summingInt(Map.Entry::getValue)));
        this.money = res;
    }
}
