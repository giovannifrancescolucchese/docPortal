package it.euris.testdoubles;

import it.euris.model.Patient;
import it.euris.service.patient.PatientService;

public class PatientServiceDummy implements PatientService {

    @Override
    public boolean haveResponsefromDeviceinLast5days(Patient patient) {
        return false;
    }

    @Override
    public boolean last3MeasuresOutOfRange(Patient patient) {
        return false;
    }

    @Override
    public void deleteLogBeforeThisYear(Patient patient) {

    }
}
