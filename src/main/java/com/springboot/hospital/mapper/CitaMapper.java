package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Medico;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CitaMapper {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public CitaDTO toDTO(Cita cita){
        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setId(cita.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedFecha = sdf.format(cita.getFecha());

        citaDTO.setFecha(formatedFecha);
        citaDTO.setCancelado(cita.isCancelado());
        citaDTO.setStatusCita(cita.getStatusCita().name());
        citaDTO.setPacienteId(cita.getPaciente().getId());
        citaDTO.setMedicoId(cita.getMedico().getId());

        return citaDTO;
    }

    public Cita toEntity(CitaDTO citaDTO, Paciente paciente, Medico medico) throws ParseException {
        Cita cita = new Cita();
        cita.setId(citaDTO.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());

        cita.setFecha(fecha);
        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        return cita;
    }

    public Cita toEntity(CitaDTO citaDTO) throws ParseException {
        Cita cita = new Cita();
        cita.setId(citaDTO.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = sdf.parse(citaDTO.getFecha());

        cita.setFecha(fecha);
        cita.setCancelado(citaDTO.isCancelado());
        cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));

        Paciente paciente = pacienteRepository.findById(citaDTO.getPacienteId()).orElse(null);
        cita.setPaciente(paciente);

        Medico medico = medicoRepository.findById(citaDTO.getMedicoId()).orElse(null);
        cita.setMedico(medico);

        return cita;
    }
}
