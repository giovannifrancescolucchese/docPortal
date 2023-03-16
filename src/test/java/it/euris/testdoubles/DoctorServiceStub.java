package it.euris.testdoubles;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import it.euris.model.PressureLog;
import it.euris.service.doctor.DoctorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorServiceStub implements DoctorService {

    @Override
    public boolean addPatientToDoctor(Patient patient, Doctor doctor) {
        return false;
    }

    @Override
    public List<Patient> getPatientsOutOfRange(Doctor doctor) {
        ArrayList<Patient> patients = new ArrayList<>();
        PressureLog log=new PressureLog(1L, LocalDate.now().minusDays(10), 120);
        List<PressureLog> logs=new ArrayList<>();
        PressureDevice pressureDevice=new PressureDevice(1L, logs);
        Patient patient = new Patient(1L, "", "", "", "",'M', LocalDate.now(), pressureDevice);
        patients.add(patient);
        return patients;
    }

    @Override
    public List<Patient> getPatientsNoResponse(Doctor doctor) {
        ArrayList<Patient> patients = new ArrayList<>();
        PressureLog log=new PressureLog(1L, LocalDate.now().minusDays(10), 120);
        List<PressureLog> logs=new ArrayList<>();
        PressureDevice pressureDevice=new PressureDevice(1L, logs);
        Patient patient = new Patient(1L, "", "", "", "",'M', LocalDate.now(), pressureDevice);
        patients.add(patient);
        return patients;
    }
}
