package ru.otus;
import org.junit.jupiter.api.*;
import ru.otus.devices.*;
import ru.otus.devices.interfaces.*;
import ru.otus.devices.interfaces.DeviceBankConnector;
import ru.otus.devices.interfaces.DeviceDispenser;

import static org.assertj.core.api.Assertions.assertThat;

public class ATMTest {
    @Test
    void ATMTestDispense() {
        DeviceCassette cassette = new DeviceImplFullCassette();
        DeviceReceiver receiver = new DeviceImplRandomReceiver();
        DeviceBankConnector bank = new DeviceImplBankConnector();
        DeviceDispenser dispenser = new DeviceImplDispenser();
        ATM atm = new ATM(bank=bank,receiver=receiver,dispenser=dispenser,cassette=cassette);
        atm.dispenseAmount(1, 112, 1150);
    }

    @Test
    void ATMTestReceive() {
        DeviceCassette cassette = new DeviceImplFullCassette();
        DeviceReceiver receiver = new DeviceImplRandomReceiver();
        DeviceBankConnector bank = new DeviceImplBankConnector();
        DeviceDispenser dispenser = new DeviceImplDispenser();
        ATM atm = new ATM(bank=bank,receiver=receiver,dispenser=dispenser,cassette=cassette);
        atm.receiveAmount(1, 112);
    }

    @Test
    void ATMTestBadBankReceive() {
        DeviceCassette cassette = new DeviceImplFullCassette();
        DeviceReceiver receiver = new DeviceImplRandomReceiver();
        DeviceBankConnector bank = new DeviceImplAngryBankConnector();
        DeviceDispenser dispenser = new DeviceImplDispenser();
        ATM atm = new ATM(bank=bank,receiver=receiver,dispenser=dispenser,cassette=cassette);
        atm.receiveAmount(1, 112);
    }

}
