package com.springboot.hospital.service;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MedicoService {

    List<MedicoDTO> getAllMedicos();

    Optional<MedicoDTO> getMedicoById(Long id);

    MedicoDTO createMedico(MedicoDTO medicoDTO);

    MedicoDTO updateMedico(Long id, MedicoDTO medicoDTO);

    void deleteMedico(Long id);

    Collection<CitaDTO> getCitasByMedicoId(Long id);

    List<MedicoDTO> getMedicosByEspecialidad(String especialidad);
}
