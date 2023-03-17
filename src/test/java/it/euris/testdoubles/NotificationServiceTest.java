package it.euris.testdoubles;

import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.service.notification.NotificationService;
import it.euris.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationServiceTest {

    private NotificationService notificationService;

    @Test
    void givenEmptyPatientListThenPrepareEmptyNotificationNoResponse () {
        //arrange
        List<Patient> patients = new ArrayList<>();
        //act
        notificationService = new NotificationServiceImpl();
        String notification = notificationService.prepareNotificationNoResponse(patients);
        //assert
        assertEquals("", notification);
    }

    @Test
    void givenEmptyPatientListThenPrepareEmptyNotificationOutOfRange () {
        //arrange
        List<Patient> patients = new ArrayList<>();
        //act
        notificationService = new NotificationServiceImpl();
        String notification = notificationService.prepareNotificationOutOfRange(patients);
        //assert
        assertEquals("", notification);
    }

    @Test
    void givenOnePatientInPatientListThenPrepareNotificationNoResponseOfOnePatient () {
        //arrange
        List<Patient> patients = new ArrayList<>();
        PressureDevice pressureDevice=new PressureDevice(new Long(1),new ArrayList<>());
        patients.add(new Patient(
                new Long(1),
                "Andrea",
                "Majcen",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                pressureDevice
        ));
        //act
        notificationService = new NotificationServiceImpl();
        String notification = notificationService.prepareNotificationNoResponse(patients);
        //assert
        assertEquals("Andrea Majcen\n", notification);
    }
}
