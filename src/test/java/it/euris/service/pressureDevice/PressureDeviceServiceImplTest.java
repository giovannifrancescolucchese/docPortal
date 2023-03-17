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

    PressureDevice pressureDevice;
    PressureDeviceService pressureDeviceService;

    @BeforeEach
    void init(){
        pressureDevice = new PressureDevice(1L, new ArrayList<>());
        pressureDeviceService = new PressureDeviceServiceImpl();
    }

    @Test
    void givenOldLogsWhenDelteingThemThenExpectedAListOfLogsInPressureDeviceWithoutTheOldOnes(){
        //INDIVIDUATO UN PROBLEMA DI MODIFICA CONCORRENTE NEL PRESSURE DEVICE SERVICE
        //arrange
        PressureLog log1 = new PressureLog(1L, LocalDate.now().minusDays(3), 100);
        PressureLog log2 = new PressureLog(1L, LocalDate.now().minusDays(2), 100);
        PressureLog log3 = new PressureLog(1L, LocalDate.now().minusDays(1), 100);
        List<PressureLog> logs = new ArrayList<>();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        //act
        pressureDevice.setPressureLogs(logs);
        pressureDeviceService.deleteLogsBeforeDate(LocalDate.now(), pressureDevice);
        //assert
        assertEquals(0, pressureDevice.getPressureLogs().size());
    }
}