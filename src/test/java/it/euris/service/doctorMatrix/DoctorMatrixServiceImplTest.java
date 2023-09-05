package it.euris.service.doctorMatrix;

import it.euris.model.Doctor;
import it.euris.model.Patient;
import it.euris.model.PressureDevice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoctorMatrixServiceImplTest {



    @Mock
    PressureDevice pressureDevice;

    @Test
    @DisplayName("data bla bla ...")
    void given49PatientOver70yearOldWhenCanAddPatientToDoctorThenTrue() {
        //arrange
        List<Patient> pazienti=new ArrayList<>();
        for (int i=1;i<=49;i++) {
            pazienti.add(new Patient(0L,"","","","",'M', LocalDate.now().minusYears(80), pressureDevice)
            );
        }
        Doctor doctor=new Doctor(0L,"","","","",pazienti,true);
        Patient newPatient=new Patient(0L,"","","","",'M', LocalDate.now(), pressureDevice);

        boolean result = false;
        DoctorMatrixService doctorMatrixService=new DoctorMatrixServiceImpl();
        //act
        result=doctorMatrixService.canAddPatientToDoctor(newPatient, doctor);
        //assert
        assertTrue(result);
    }


    @ParameterizedTest(name="Given {0} patients of {1} years average age when try to add a patient of {2} years then return {3}  ")
    @CsvSource({
            "0,  0, 0, true",
            "80, 45, 23, false"
    })
    void parametrizedTestCanAddPatientToDoctor(int numPatients, int averagePatientAge, int ageNewPatient, boolean expectedResult) {
        //arrange
        List<Patient> pazienti=new ArrayList<>();
        for (int i=1;i<=numPatients;i++) {
            pazienti.add(new Patient(0L,"","","","",'M', LocalDate.now().minusYears(averagePatientAge), pressureDevice)
            );
        }
        Doctor doctor=new Doctor(0L,"","","","",pazienti,true);
        Patient newPatient=new Patient(0L,"","","","",'M', LocalDate.now().minusYears(ageNewPatient), pressureDevice);

        boolean result = false;
        DoctorMatrixService doctorMatrixService=new DoctorMatrixServiceImpl();
        //act
        result=doctorMatrixService.canAddPatientToDoctor(newPatient, doctor);
        //assert
        assertEquals(result,expectedResult);
    }





}