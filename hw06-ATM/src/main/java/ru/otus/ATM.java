package ru.otus;

import ru.otus.devices.interfaces.IDeviceBankConnector;
import ru.otus.devices.interfaces.IDeviceCassette;
import ru.otus.devices.interfaces.IDeviceDispenser;
import ru.otus.devices.interfaces.IDeviceReceiver;
import ru.otus.enums.EOperationType;
import ru.otus.units.*;

import java.util.ArrayList;

public class ATM {
    private IUnit circuitReceive;
    private IUnit circuitDispense;

    ATM(IDeviceBankConnector bank, IDeviceReceiver receiver, IDeviceDispenser dispenser, IDeviceCassette cassette){
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
        ITransaction t = new Transaction(EOperationType.AMOUNT_RECEIVING, userId, accountId);
        circuitReceive.Process(t);
        System.out.println(t.getLog());
    }
    public void dispenseAmount(int userId, int accountId, int amount) {
        ITransaction t = new Transaction(EOperationType.AMOUNT_DISPENSING, userId, accountId, amount);
        circuitDispense.Process(t);
        System.out.println(t.getLog());
    }
}