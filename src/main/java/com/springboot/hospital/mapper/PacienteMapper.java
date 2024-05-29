package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.model.Paciente;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO pacienteDTO = new PacienteDTO();

        BeanUtils.copyProperties(paciente, pacienteDTO);
        return pacienteDTO;
    }

    public Paciente toEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();

        BeanUtils.copyProperties(pacienteDTO, paciente);
        return paciente;
    }
}
