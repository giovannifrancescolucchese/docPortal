package it.euris.service.doctor;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.service.doctorMatrix.DoctorMatrixService;
import it.euris.service.patient.PatientService;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

public class DoctorServiceImpl implements DoctorService {
    private final PatientService patientService;

    private final DoctorMatrixService doctorMatrixService;

    public DoctorServiceImpl(PatientService patientService, DoctorMatrixService doctorMatrixService) {
        this.patientService = patientService;
        this.doctorMatrixService = doctorMatrixService;
    }

    @Override
    public boolean addPatientToDoctor(Patient patient, Doctor doctor) {
        boolean canAdd=false;
        if (doctor.getMorePatients()) {
            canAdd = doctorMatrixService.canAddPatientToDoctor(patient, doctor);
            if (canAdd)
                doctor.getPatients().add(patient);
        }
        doctor.setMorePatients(canAdd);
        return canAdd;
    }

    @Override
    public List<Patient> getPatientsOutOfRange(Doctor doctor) {
        List<Patient> patients=new ArrayList<>();
        for (Patient patient : doctor.getPatients()) {
            if (patientService.last3MeasuresOutOfRange(patient)) patients.add(patient);
        }
        return patients;
    }

    @Override
    public List<Patient> getPatientsNoResponse(Doctor doctor) {
        List<Patient> patients=new ArrayList<>();
        for (Patient patient : doctor.getPatients()) {
            if (!patientService.haveResponsefromDeviceinLast5days(patient)) patients.add(patient);
        }
        return patients;
    }


}
