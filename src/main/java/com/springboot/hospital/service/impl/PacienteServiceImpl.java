package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.PacienteMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.repository.PacienteRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<PacienteDTO> getAllPacientes() {
        return pacienteRepository.findAll().stream()
                .map(pacienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PacienteDTO> getPacienteById(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);

        return optionalPaciente.map(pacienteMapper::toDTO);

    }

    @Override
    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);

        return pacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);

        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            paciente.setNombre(pacienteDTO.getNombre());
            paciente.setEnfermedad(pacienteDTO.isEnfermedad());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());

            paciente = pacienteRepository.save(paciente);
            return pacienteMapper.toDTO(paciente);
        }
        return null;
    }

    @Override
    public void deletePaciente(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if (optionalPaciente.isPresent()){
            Paciente paciente = optionalPaciente.get();

            for (Cita cita : paciente.getCitas()){
                citaService.deleteCita(cita.getId());
            }

            pacienteRepository.deleteById(id);
        }

    }

    @Override
    public Collection<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(pacienteId);
        return optionalPaciente
                .map(paciente -> paciente.getCitas().stream()
                        .map(citaMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
