package it.euris.testdoubles;

import static org.junit.jupiter.api.Assertions.*;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.mail.MailService;
import it.euris.service.mail.MailServiceImpl;
import it.euris.service.notification.NotificationService;
import it.euris.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MailServiceTest {



    DoctorServiceMock doctorService;
    DoctorServiceStub doctorServiceStub;
    NotificationService notificationService;


    @BeforeEach
    void setup() {
        doctorService=new DoctorServiceMock();
        notificationService=new NotificationServiceImpl();
        doctorServiceStub=new DoctorServiceStub();
    }


    //MOCK voglio testare la corretta interazione tra mailservice e notificationService
    @Test
    void givenPatientsOutOfRangeNotNullSendMailNotification() {
        //arrange
        Doctor doctor=new Doctor(1L, "nomeDottore","cognomeDotttore","Via Garibaldi 1", "dottore@email.it", null, true);
        LocalDate startDate=LocalDate.now().minusDays(3);
        List<PressureLog> pressureLogList=new ArrayList<>();
        for (int i=0; i<3; i++) {
            PressureLog pressureLog=new PressureLog(new Long(i), startDate.plusDays(i),100);
            pressureLogList.add(pressureLog);
        }
        Patient patient=new Patient(
                new Long(1),
                "nome",
                "cognome",
                "indirizzo",
                "email",
                'M',
                LocalDate.of(1915, Month.JULY, 29),
                new PressureDevice(1L,pressureLogList)
        );
        MailService mailService=new MailServiceImpl(notificationService, doctorService);
        //act
        //non sono interessato alla logica di aggiunta paziente
        doctorService.addPatientToDoctor(patient,doctor);
        String message=mailService.sendNotifications(doctor);
        //assert

        assertNotEquals(message, "");
        assertTrue(message.contains("OUT OF RANGE:"));
        assertFalse(message.contains("NO RESPONSE:"));

        doctorService.verify();
    }

    @Test
    void givenPatientsNoResponseNotNullSendMailNotification(){
        Doctor doctor=new Doctor(1L, "nomeDottore","cognomeDotttore","Via Garibaldi 1", "dottore@email.it", null, true);

        MailService mailService=new MailServiceImpl(notificationService, doctorServiceStub);
        assertNotEquals(mailService.sendNotifications(doctor), "");
    }


}
