package com.springboot.hospital.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PacienteDTO {

    private Long id;
    private String nombre;
    private Date fechaNacimiento;
    private boolean enfermedad;
}
