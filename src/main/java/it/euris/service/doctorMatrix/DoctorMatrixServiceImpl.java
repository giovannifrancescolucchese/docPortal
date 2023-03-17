package it.euris.service.doctorMatrix;

import it.euris.model.Doctor;
import it.euris.model.Patient;

import java.util.IntSummaryStatistics;

public class DoctorMatrixServiceImpl implements DoctorMatrixService {
    @Override
    public boolean canAddPatientToDoctor(Patient patient, Doctor doctor) {
        IntSummaryStatistics stats = doctor.getPatients()
                .stream()
                .mapToInt(Patient::getAge)
                .summaryStatistics();
        double averageAge=stats.getAverage();
        if (    ((averageAge>=0) && (averageAge<=50) && doctor.getPatients().size()<80) ||
                ((averageAge>50) && (averageAge<=60) && doctor.getPatients().size()<70) ||
                ((averageAge>60) && (averageAge<=70) && doctor.getPatients().size()<60) ||
                ((averageAge>70) && doctor.getPatients().size()<50)
        ) return true;
    return false;
    }

    //TODO: bla bla

}
