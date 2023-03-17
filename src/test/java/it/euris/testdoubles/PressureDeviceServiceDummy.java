package it.euris.testdoubles;

import it.euris.model.PressureDevice;
import it.euris.service.pressureDevice.PressureDeviceService;

import java.time.LocalDate;

public class PressureDeviceServiceDummy implements PressureDeviceService {
    @Override
    public void deleteLogsBeforeDate(LocalDate date, PressureDevice pressureDevice) {
    }
}
