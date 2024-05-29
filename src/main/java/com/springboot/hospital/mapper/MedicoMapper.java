package com.springboot.hospital.mapper;

import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.model.Medico;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public MedicoDTO toDto(Medico medico){
        MedicoDTO medicoDTO = new MedicoDTO();
        /*medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setEspecialidad(medico.getEspecialidad());*/
        BeanUtils.copyProperties(medico, medicoDTO);
        return medicoDTO;
    }

    public Medico toEntity(MedicoDTO medicoDTO){
        Medico medico = new Medico();
        /*medicoDTO.setId(medico.getId());
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setEspecialidad(medico.getEspecialidad());*/
        BeanUtils.copyProperties(medicoDTO, medico);
        return medico;
    }
}
