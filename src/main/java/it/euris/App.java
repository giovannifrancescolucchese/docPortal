package it.euris;

import it.euris.model.Doctor;import it.euris.model.Patient;import it.euris.model.PressureDevice;import it.euris.model.PressureLog;import it.euris.service.doctor.DoctorService;import it.euris.service.doctor.DoctorServiceImpl;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.doctorMatrix.DoctorMatrixServiceImpl;
import it.euris.service.mail.MailService;import it.euris.service.mail.MailServiceImpl;import it.euris.service.notification.NotificationService;import it.euris.service.notification.NotificationServiceImpl;import it.euris.service.patient.PatientService;import it.euris.service.patient.PatientServiceImpl;
import it.euris.service.pressureDevice.PressureDeviceService;
import it.euris.service.pressureDevice.PressureDeviceServiceImpl;

import java.time.LocalDate;
import java.time.Month;import java.util.ArrayList;

/**
 * docPortal application
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //inizializzazione degli oggetti "highly coupled"
        DoctorMatrixService doctorMatrixService=new DoctorMatrixServiceImpl();
        PressureDeviceService pressureDeviceService=new PressureDeviceServiceImpl();
        PatientService patientService=new PatientServiceImpl(pressureDeviceService);
        DoctorService doctorService=new DoctorServiceImpl(patientService, doctorMatrixService);
        NotificationService notificationService=new NotificationServiceImpl();
        MailService mailService=new MailServiceImpl(notificationService,doctorService);

        //esempio ipotetico di utilizzo
        System.out.println( "Creazione  nuovo dottore..." );
        Doctor doctor=new Doctor(1L, "nomeDottore","cognomeDotttore","Via Garibaldi 1", "dottore@email.it", new ArrayList<>(), true);
        System.out.println( "Creazione  nuovo dottore avvenuta correttamente!" );
        System.out.println( "Associazione pazienti..." );
        for (int i=0;i<=100;i++) {
            PressureDevice pressureDevice=new PressureDevice(new Long(i),new ArrayList<>());
            LocalDate startMeasures=LocalDate.of(2022, Month.MARCH,17);
            for (int j=1;j<=365;j++) {
                PressureLog pressureLog=new PressureLog(new Long(j), startMeasures,200);
                pressureDevice.getPressureLogs().add(pressureLog);
                startMeasures=startMeasures.plusDays(1L);
            }
            Patient patient=new Patient(
                    new Long(i),
                    "nome"+i,
                    "cognome"+i,
                    "indirizzo",
                    "email",
                    'M',
                    LocalDate.of(1915, Month.JULY, 29),
                    pressureDevice
                    );
            System.out.println(String.format("Associazione paziente %d al dottore...",i));
            if (doctorService.addPatientToDoctor(patient,doctor))
                System.out.println(String.format("Associato paziente %d al dottore...",i));
            else System.out.println(String.format("NON è stato possibile associare il paziente %d al dottore...",i));
        }
        System.out.println("***********************************************************************");
        System.out.println(mailService.sendNotifications(doctor));
    }
}
