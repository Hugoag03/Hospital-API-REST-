package com.springboot.hospital.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ConsultaDTO {

    private Long id;
    private String fechaConsulta;
    private String informe;
    private CitaDTO citaDTO;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Date getFechaConsultaAsDate() throws ParseException {
        return dateFormat.parse(this.fechaConsulta);
    }

    public void setFechaConsultaFromDate(Date fechaConsulta) {
        this.fechaConsulta = dateFormat.format(fechaConsulta);
    }
}
