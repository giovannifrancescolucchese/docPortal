package it.euris.testdoubles;

import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.pressureDevice.PressureDeviceService;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PressureDeviceServiceDummy implements PressureDeviceService {
    @Override
    public void deleteLogsBeforeDate(LocalDate date, PressureDevice pressureDevice) {}
    

}
