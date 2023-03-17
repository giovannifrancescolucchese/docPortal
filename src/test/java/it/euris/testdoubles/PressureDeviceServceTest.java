package it.euris.testdoubles;

import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.pressureDevice.PressureDeviceService;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PressureDeviceServceTest {

    PressureDeviceService pressureDeviceService;

    @BeforeEach
    void setup() {
        pressureDeviceService = new PressureDeviceServiceImpl();
    }

    @Test
    void givenListOfLogsThenAfterEliminationListIsEmpty() {
        PressureDevice pressureDevice = new PressureDevice(new Long(1), new ArrayList<>());
        PressureLog pressureLog = new PressureLog(new Long(1), LocalDate.now().minusDays(3L), 200);
        PressureLog pressureLog1 = new PressureLog(new Long(2), LocalDate.now().minusDays(2L), 180);
        PressureLog pressureLog2 = new PressureLog(new Long(3), LocalDate.now().minusDays(1L), 160);
        List<PressureLog> pressureLogs = pressureDevice.getPressureLogs();
        pressureLogs.add(pressureLog);
        pressureLogs.add(pressureLog1);
        pressureLogs.add(pressureLog2);

        pressureDeviceService.deleteLogsBeforeDate(LocalDate.now(), pressureDevice);
        assertTrue(pressureDevice.getPressureLogs().isEmpty());

    }
}
