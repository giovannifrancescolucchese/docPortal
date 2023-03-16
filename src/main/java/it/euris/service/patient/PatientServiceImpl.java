package it.euris.service.patient;

import it.euris.model.Patient;
import it.euris.model.PressureLog;
import it.euris.service.pressureDevice.PressureDeviceService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class PatientServiceImpl implements PatientService{

    private final PressureDeviceService pressureDeviceService;

    public PatientServiceImpl(PressureDeviceService pressureDeviceService) {
        this.pressureDeviceService = pressureDeviceService;
    }


    @Override
    public boolean haveResponsefromDeviceinLast5days(Patient patient) {
        Optional<PressureLog> lastPressure=patient.getPressureDevice().getPressureLogs().stream().sorted(Comparator.comparing(PressureLog::getDate).reversed()).findFirst();
        return (lastPressure.isPresent() && (DAYS.between(lastPressure.get().getDate(),LocalDate.now())<=5));
    }

    @Override
    public boolean last3MeasuresOutOfRange(Patient patient) {
        List<PressureLog> last3pressureLogs= patient.getPressureDevice().getPressureLogs().stream().sorted(Comparator.comparing(PressureLog::getDate).reversed()).collect(Collectors.toList()).subList(0,2);

        if (patient.getSex()=='F') {
            if ((patient.getAge()>=0) && (patient.getAge()<=50)) {
                return allOutOfRange(last3pressureLogs, 60, 140);
            }
            else if ((patient.getAge()>50) && (patient.getAge()<=60)) {
                return allOutOfRange(last3pressureLogs, 70, 130);
            }
            else if ((patient.getAge()>60) && (patient.getAge()<=70)) {
                return allOutOfRange(last3pressureLogs, 85, 125);
            }
            else if (patient.getAge()>70) {
                return allOutOfRange(last3pressureLogs, 80, 120);
            }
        }
        else if (patient.getSex()=='M') {
            if ((patient.getAge()>=0) && (patient.getAge()<=50)) {
                return allOutOfRange(last3pressureLogs, 70, 130);
            }
            else if ((patient.getAge()>50) && (patient.getAge()<=60)) {
                return allOutOfRange(last3pressureLogs, 75, 125);
            }
            else if ((patient.getAge()>60) && (patient.getAge()<=70)) {
                return allOutOfRange(last3pressureLogs, 85, 120);
            }
            else if (patient.getAge()>70) {
                return allOutOfRange(last3pressureLogs, 80, 120);
            }
        }
        return false;
    }

    @Override
    public void deleteLogBeforeThisYear(Patient patient) {
        pressureDeviceService.deleteLogsBeforeDate(
                LocalDate.of(LocalDate.now().getYear(), Month.JANUARY,1),
                patient.getPressureDevice()
                );
    }

    private boolean allOutOfRange(List<PressureLog> list, int min, int max) {
        boolean result=true;
        for (PressureLog pressureLog : list) {
            result&=!((pressureLog.getValue()>=min)&&(pressureLog.getValue()<=max));
        }
        return result;
    }
}
