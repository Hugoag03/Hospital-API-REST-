package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.model.*;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ConsultaMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public ConsultaDTO toDTO(Consulta consulta) throws ParseException {
        ConsultaDTO consultaDTO = new ConsultaDTO();

        consultaDTO.setId(consulta.getId());
        consultaDTO.setFechaConsulta(dateFormat.format(consulta.getFechaConsulta()));
        consultaDTO.setInforme(consulta.getInforme());

        if (consulta.getCita() != null) {
            Cita cita = consulta.getCita();
            CitaDTO citaDTO = new CitaDTO();

            citaDTO.setId(cita.getId());
            citaDTO.setFecha(dateFormat.format(cita.getFecha()));
            citaDTO.setCancelado(cita.isCancelado());
            citaDTO.setStatusCita(cita.getStatusCita().toString());
            citaDTO.setPacienteId(cita.getPaciente().getId());
            citaDTO.setMedicoId(cita.getMedico().getId());

            consultaDTO.setCitaDTO(citaDTO);
        }
        return consultaDTO;
    }

    public Consulta toEntity(ConsultaDTO consultaDTO) throws ParseException {
        Consulta consulta = new Consulta();

        consulta.setId(consultaDTO.getId());
        consulta.setFechaConsulta(dateFormat.parse(consultaDTO.getFechaConsulta()));
        consulta.setInforme(consultaDTO.getInforme());

        if (consultaDTO.getCitaDTO() != null) {
            CitaDTO citaDTO = consultaDTO.getCitaDTO();
            Cita cita = new Cita();


            cita.setId(citaDTO.getId());
            cita.setFecha(dateFormat.parse(citaDTO.getFecha()));
            cita.setCancelado(citaDTO.isCancelado());
            cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

            Paciente paciente = pacienteRepository.findById(citaDTO.getPacienteId()).orElse(null);
            Medico medico = medicoRepository.findById(citaDTO.getMedicoId()).orElse(null);

            cita.setPaciente(paciente);
            cita.setMedico(medico);

            consulta.setCita(cita);
        }
        return consulta;
    }
}
