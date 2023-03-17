package it.euris.service.pressureDevice;

import it.euris.model.PressureDevice;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class PressureDeviceServiceImpl implements PressureDeviceService {
    @Override
    public void deleteLogsBeforeDate(LocalDate date, PressureDevice pressureDevice) {
        pressureDevice.setPressureLogs(pressureDevice.getPressureLogs().stream()
                .filter(pressureLog -> pressureLog.getDate().isAfter(date))
                .collect(Collectors.toList()));
    }
}