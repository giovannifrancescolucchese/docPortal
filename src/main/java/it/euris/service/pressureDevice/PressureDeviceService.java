package it.euris.service.pressureDevice;

import it.euris.model.PressureDevice;

import java.time.LocalDate;

public interface PressureDeviceService {

    void deleteLogsBeforeDate(LocalDate date, PressureDevice pressureDevice);
}
