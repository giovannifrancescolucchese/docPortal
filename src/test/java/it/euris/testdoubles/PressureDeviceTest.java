package it.euris.testdoubles;

import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.pressureDevice.PressureDeviceService;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PressureDeviceTest {

    private PressureDeviceService pressureDeviceService;

    @Test
    void givenTodayDateAndOlderPressureLogsThenDeleteAllPressureLogs(){
        //arrange
        LocalDate today = LocalDate.now();
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(50L),200);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now().minusDays(30L),200);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now().minusDays(1L),200);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        //act
        pressureDeviceService = new PressureDeviceServiceImpl();
        pressureDeviceService.deleteLogsBeforeDate(today, pressureDevice);
        //assert
        assertTrue(pressureDevice.getPressureLogs().isEmpty());
    }

    @Test
    void givenTodayMinus10DaysDateAndTodayMinus9DaysPressureLogsThenDeleteNoOnePressureLogs(){
        //arrange
        LocalDate today = LocalDate.now().minusDays(10L);
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(9L),200);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now().minusDays(5L),200);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now(),200);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        //act
        pressureDeviceService = new PressureDeviceServiceImpl();
        pressureDeviceService.deleteLogsBeforeDate(today, pressureDevice);
        //assert
        assertFalse(pressureDevice.getPressureLogs().isEmpty());
        assertEquals(3, pressureDevice.getPressureLogs().size());
    }
    @Test
    void givenTodayDateAndTodayPressureLogsThenDeleteAllPressureLogs(){
        //arrange
        LocalDate today = LocalDate.now();
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now(),200);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now(),200);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now(),200);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        //act
        pressureDeviceService = new PressureDeviceServiceImpl();
        pressureDeviceService.deleteLogsBeforeDate(today, pressureDevice);
        //assert
        assertTrue(pressureDevice.getPressureLogs().isEmpty());
    }

    @Test
    void givenTodayMinus10DaysDateAndTwoPressureLogsOlderAndOnePressureLogNewerThenDeleteTwoOlderPressureLogs(){
        //arrange
        LocalDate today = LocalDate.now().minusDays(10L);
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        PressureLog pressureLog=new PressureLog(new Long(1), LocalDate.now().minusDays(10L),200);
        PressureLog pressureLog2=new PressureLog(new Long(1), LocalDate.now().minusDays(11L),200);
        PressureLog pressureLog3=new PressureLog(new Long(1), LocalDate.now(),200);
        pressureDevice.getPressureLogs().add(pressureLog);
        pressureDevice.getPressureLogs().add(pressureLog2);
        pressureDevice.getPressureLogs().add(pressureLog3);
        //act
        pressureDeviceService = new PressureDeviceServiceImpl();
        pressureDeviceService.deleteLogsBeforeDate(today, pressureDevice);
        //assert
        assertFalse(pressureDevice.getPressureLogs().isEmpty());
        assertEquals(1, pressureDevice.getPressureLogs().size());
    }
}
