package it.euris.service.mail;

import it.euris.model.Doctor;

public interface MailService {

    String sendNotifications(Doctor doctor);
}
