package ru.otus;

import ru.otus.devices.interfaces.DeviceBankConnector;
import ru.otus.devices.interfaces.DeviceCassette;
import ru.otus.devices.interfaces.DeviceDispenser;
import ru.otus.devices.interfaces.DeviceReceiver;
import ru.otus.enums.EOperationType;
import ru.otus.units.*;

public class ATM {
    private Unit circuitReceive;
    private Unit circuitDispense;

    public ATM(DeviceBankConnector bank, DeviceReceiver receiver, DeviceDispenser dispenser, DeviceCassette cassette){
        circuitReceive = new UnitReceiver(receiver);

        circuitReceive
                .setNextUnit(new UnitBankConnector(bank))
                .setNextUnit(new UnitCassette(cassette));

        circuitDispense = new UnitCassette(cassette);

        circuitDispense
                .setNextUnit(new UnitBankConnector(bank))
                .setNextUnit(new UnitDispenser(dispenser));
    }
    public void receiveAmount(int userId, int accountId){
        Context t = new ContextImpl(EOperationType.AMOUNT_RECEIVING, userId, accountId);
        circuitReceive.Process(t);
        System.out.println(t.getLog());
    }
    public void dispenseAmount(int userId, int accountId, int amount) {
        Context t = new ContextImpl(EOperationType.AMOUNT_DISPENSING, userId, accountId, amount);
        circuitDispense.Process(t);
        System.out.println(t.getLog());
    }
}