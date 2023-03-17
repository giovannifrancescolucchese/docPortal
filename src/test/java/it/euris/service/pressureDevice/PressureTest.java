package it.euris.service.pressureDevice;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PressureTest {

    @Test
    void checkCreationOfPressureDeviceAndPressureLog(){
        PressureLog pressureLog=new PressureLog(1L, LocalDate.now(),120);
        List<PressureLog> logs=new ArrayList<>();
        logs.add(pressureLog);
        PressureDevice device=new PressureDevice(1L,logs);

        assertEquals(1L,pressureLog.getId());
        assertEquals(LocalDate.now(),pressureLog.getDate());
        assertEquals(120,pressureLog.getValue());

        assertEquals(1L,device.getId());
        assertEquals(1,device.getPressureLogs().size());
    }
}
