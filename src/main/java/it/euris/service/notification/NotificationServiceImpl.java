package it.euris.service.notification;

import it.euris.model.Patient;
import it.euris.model.PressureLog;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationServiceImpl implements NotificationService {


    @Override
    public String prepareNotificationOutOfRange(List<Patient> patients) {
        String notification = "";
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                if (patient.getPressureDevice().getPressureLogs().size() >= 3) {
                    notification += String.format("%s %s\t", patient.getName(), patient.getSurname());
                    List<PressureLog> last3pressureLogs = patient.getPressureDevice().getPressureLogs().stream().sorted(Comparator.comparing(PressureLog::getDate).reversed()).collect(Collectors.toList()).subList(0, 2);
                    for (PressureLog pressureLog : last3pressureLogs) {
                        notification += String.format("misurazione %d del %s\n",
                                pressureLog.getValue(),
                                pressureLog.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                    }
                }
            }
        }
        return notification;
    }

    @Override
    public String prepareNotificationNoResponse(List<Patient> patients) {
        String notification = "";
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                notification += String.format("%s %s\n", patient.getName(), patient.getSurname());
            }
        }
        return notification;
    }

}
