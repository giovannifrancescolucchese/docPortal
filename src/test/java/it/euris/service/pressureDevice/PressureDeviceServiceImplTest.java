package it.euris.service.pressureDevice;

import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PressureDeviceServiceImplTest {
    private PressureDeviceServiceImpl pressureDeviceService;

    private PressureDevice pressureDevice;

    @BeforeEach
    void setUp() {
        pressureDeviceService = new PressureDeviceServiceImpl();
        pressureDevice = new PressureDevice(1L, new ArrayList<>());
    }

    @Test
    void deleteLogsBeforeDate_shouldRemoveOldLogs() {
        // crea alcuni log di test
        List<PressureLog> logs = new ArrayList<>();
        logs.add(new PressureLog(1L, LocalDate.now().minusDays(10), 120));
        logs.add(new PressureLog(2L, LocalDate.now().minusDays(5), 130));
        logs.add(new PressureLog(3L, LocalDate.now().minusDays(2), 125));
        logs.add(new PressureLog(4L, LocalDate.now(), 135));

        // imposta i log sul dispositivo
        pressureDevice.setPressureLogs(logs);

        // cancella i log prima di 7 giorni fa
        pressureDeviceService.deleteLogsBeforeDate(LocalDate.now().minusDays(7), pressureDevice);

        // assert che i vecchi log sono stati rimossi
        assertFalse(pressureDevice.getPressureLogs().contains(logs.get(0)));
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(1)));
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(2)));
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(3)));
    }

    @Test
    void deleteLogsBeforeDate_shouldNotRemoveRecentLogs() {
        // crea test logs
        List<PressureLog> logs = new ArrayList<>();
        logs.add(new PressureLog(1L, LocalDate.now().minusDays(3), 120));
        logs.add(new PressureLog(2L, LocalDate.now().minusDays(2), 130));
        logs.add(new PressureLog(3L, LocalDate.now().minusDays(1), 125));
        logs.add(new PressureLog(4L, LocalDate.now(), 135));

        // imposta i log sul dispositivo
        pressureDevice.setPressureLogs(logs);

        // cancella i log prima di 7 giorni fa
        pressureDeviceService.deleteLogsBeforeDate(LocalDate.now().minusDays(7), pressureDevice);

        // afferma che nessun log è stato rimosso
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(0)));
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(1)));
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(2)));
        assertTrue(pressureDevice.getPressureLogs().contains(logs.get(3)));
    }

}