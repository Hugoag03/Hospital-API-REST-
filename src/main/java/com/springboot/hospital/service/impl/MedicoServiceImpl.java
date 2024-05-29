package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.MedicoMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Medico;
import com.springboot.hospital.model.Paciente;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<MedicoDTO> getAllMedicos() {
      return medicoRepository.findAll()
                .stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<MedicoDTO> getMedicoById(Long id) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        return optionalMedico.map(medicoMapper::toDTO);
    }

    @Override
    public MedicoDTO createMedico(MedicoDTO medicoDTO) {
        Medico medico = medicoMapper.toEntity(medicoDTO);
        medico = medicoRepository.save(medico);

        return medicoMapper.toDTO(medico);

    }

    @Override
    public MedicoDTO updateMedico(Long id, MedicoDTO medicoDTO) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);

        if (optionalMedico.isPresent()){
            Medico medico = optionalMedico.get();
            medico.setNombre(medicoDTO.getNombre());
            medico.setEmail(medicoDTO.getEmail());
            medico.setEspecialidad(medicoDTO.getEspecialidad());

            medico = medicoRepository.save(medico);

            return medicoMapper.toDTO(medico);
        }

        return null;
    }

    @Override
    public void deleteMedico(Long id) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);

        if (optionalMedico.isPresent()){
            Medico medico = optionalMedico.get();

            for (Cita cita : medico.getCitas()){
                citaService.deleteCita(cita.getId());
            }

            medicoRepository.deleteById(id);
        }
    }

    @Override
    public Collection<CitaDTO> getCitasByMedicoId(Long id) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        return optionalMedico.map(medico -> medico.getCitas().stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList()))
                    .orElse(null);

    }

    @Override
    public List<MedicoDTO> getMedicosByEspecialidad(String especialidad) {
        List <Medico> medicos = medicoRepository.findByEspecialidad(especialidad);
        return medicos.stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
