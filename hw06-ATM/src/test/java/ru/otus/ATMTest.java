package ru.otus;
import org.junit.jupiter.api.*;
import ru.otus.devices.*;
import ru.otus.devices.interfaces.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ATMTest {
    @Test
    void ATMTestDispense() {
        IDeviceCassette cassette = new DeviceFullCassette();
        IDeviceReceiver receiver = new DeviceRandomReceiver();
        IDeviceBankConnector bank = new DeviceBankConnector();
        IDeviceDispenser dispenser = new DeviceDispenser();
        ATM atm = new ATM(bank=bank,receiver=receiver,dispenser=dispenser,cassette=cassette);
        atm.dispenseAmount(1, 112, 1150);
    }

    @Test
    void ATMTestReceive() {
        IDeviceCassette cassette = new DeviceFullCassette();
        IDeviceReceiver receiver = new DeviceRandomReceiver();
        IDeviceBankConnector bank = new DeviceBankConnector();
        IDeviceDispenser dispenser = new DeviceDispenser();
        ATM atm = new ATM(bank=bank,receiver=receiver,dispenser=dispenser,cassette=cassette);
        atm.receiveAmount(1, 112);
    }

    @Test
    void ATMTestBadBankReceive() {
        IDeviceCassette cassette = new DeviceFullCassette();
        IDeviceReceiver receiver = new DeviceRandomReceiver();
        IDeviceBankConnector bank = new DeviceBadBankConnector();
        IDeviceDispenser dispenser = new DeviceDispenser();
        ATM atm = new ATM(bank=bank,receiver=receiver,dispenser=dispenser,cassette=cassette);
        atm.receiveAmount(1, 112);
    }

}
