package it.euris.model;

import lombok.AllArgsConstructor;import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PressureLog {
    Long id;
    LocalDate date;
    Integer value;

}
