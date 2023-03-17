package it.euris.testdoubles;

import it.euris.model.Patient;
import it.euris.service.patient.PatientService;

public class PatientServiceStub implements PatientService {
    private boolean response;

    PatientServiceStub(boolean value) {
        this.response = value;
    }

    @Override
    public boolean haveResponsefromDeviceinLast5days(Patient patient) {
        return response;
    }

    @Override
    public boolean last3MeasuresOutOfRange(Patient patient) {
        return response;
    }

    @Override
    public void deleteLogBeforeThisYear(Patient patient) {

    }
}
