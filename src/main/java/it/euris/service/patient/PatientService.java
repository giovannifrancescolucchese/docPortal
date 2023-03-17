package it.euris.service.patient;

import it.euris.model.Patient;

public interface PatientService {

    boolean haveResponsefromDeviceinLast5days(Patient patient);

    boolean last3MeasuresOutOfRange(Patient patient);

    void deleteLogBeforeThisYear(Patient patient);

}
