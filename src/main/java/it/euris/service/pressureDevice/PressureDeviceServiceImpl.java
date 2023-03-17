package it.euris.service.pressureDevice;

import it.euris.model.PressureDevice;

import java.time.LocalDate;

public class PressureDeviceServiceImpl implements PressureDeviceService {
    @Override
    public void deleteLogsBeforeDate(LocalDate date, PressureDevice pressureDevice) {
        //Così facendo si evitano problemi di concorrenza
        pressureDevice.getPressureLogs().removeIf(pressureLog1 -> pressureLog1.getDate().isBefore(date));

     /*   pressureDevice.getPressureLogs().forEach(pressureLog -> {

            pressureDevice.getPressureLogs().removeIf(pressureLog1 ->  pressureLog1.getDate().isBefore(date));
            if (pressureLog.getDate().isBefore(date))
                pressureDevice.getPressureLogs().remove(pressureLog);
        });*/
    }
}
