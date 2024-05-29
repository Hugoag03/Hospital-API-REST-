package com.springboot.hospital.service;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.PacienteDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PacienteService {

    List<PacienteDTO> getAllPacientes();

    Optional<PacienteDTO> getPacienteById(Long id);

    PacienteDTO createPaciente(PacienteDTO pacienteDTO);

    PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO);

    void deletePaciente(Long id);

    Collection<CitaDTO> getCitasByPacienteId(Long pacienteId);
}
