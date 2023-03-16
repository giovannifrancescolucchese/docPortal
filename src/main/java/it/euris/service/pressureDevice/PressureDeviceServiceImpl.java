package it.euris.service.pressureDevice;

import it.euris.model.PressureDevice;

import java.time.LocalDate;

public class PressureDeviceServiceImpl implements PressureDeviceService{
    @Override
    public void deleteLogsBeforeDate(LocalDate date, PressureDevice pressureDevice) {
        pressureDevice.getPressureLogs().forEach(pressureLog -> {
            if (pressureLog.getDate().isBefore(date))
                pressureDevice.getPressureLogs().remove(pressureLog);
        });
    }
}
