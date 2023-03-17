package it.euris.testdoubles;

        import it.euris.model.Doctor;
        import it.euris.model.Patient;
        import it.euris.service.doctor.DoctorService;

        import java.util.ArrayList;
        import java.util.List;


public class DoctorServiceMockFalse implements DoctorService {
    private List<Patient> patientList=new ArrayList<>();

    @Override
    public boolean addPatientToDoctor(Patient patient, Doctor doctor) {
        patientList.add(patient);
        return false;
    }

    @Override
    public List<Patient> getPatientsOutOfRange(Doctor doctor) {
        return null;
    }

    @Override
    public List<Patient> getPatientsNoResponse(Doctor doctor) {
        return null;
    }


}
