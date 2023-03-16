package it.euris.service.notification;

import it.euris.model.Patient;

import java.util.List;

public interface NotificationService {

    String prepareNotificationOutOfRange(List<Patient> patients);

    String prepareNotificationNoResponse(List<Patient> patients);

}
