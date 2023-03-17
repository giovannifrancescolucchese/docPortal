package it.euris.service.mail;

import it.euris.model.Doctor;
import it.euris.service.doctor.DoctorService;
import it.euris.service.notification.NotificationService;

public class MailServiceImpl implements MailService {

    private final NotificationService notificationService;
    private final DoctorService doctorService;

    public MailServiceImpl(NotificationService notificationService, DoctorService doctorService) {
        this.notificationService = notificationService;
        this.doctorService = doctorService;
    }

    @Override
    public String sendNotifications(Doctor doctor) {
        String mailnotification = "";
        String outOfRange = notificationService.prepareNotificationOutOfRange(doctorService.getPatientsOutOfRange(doctor));
        String noResponse = notificationService.prepareNotificationNoResponse(doctorService.getPatientsNoResponse(doctor));
        if (outOfRange != null || noResponse != null) {
            mailnotification += "INVIO NOTIFICHE: \n";
            if (outOfRange != null) {
                mailnotification += "OUT OF RANGE: \n";
                mailnotification += outOfRange + "\n";
            }
            if (noResponse != null) {
                mailnotification += "NO RESPONSE: \n";
                mailnotification += noResponse + "\n";
            }
        }
        return mailnotification;
    }
}
