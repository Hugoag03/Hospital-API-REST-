package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.MedicoDTO;
import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.MedicoMapper;
import com.springboot.hospital.mapper.PacienteMapper;
import com.springboot.hospital.model.*;
import com.springboot.hospital.repository.CitaRepository;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.repository.MedicoRepository;
import com.springboot.hospital.repository.PacienteRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;
import com.springboot.hospital.service.PacienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private MedicoMapper medicoMapper;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<CitaDTO> getAllCitas() {
        return citaRepository
                .findAll()
                .stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<CitaDTO> getCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        return citaOptional.map(citaMapper::toDTO);
    }

    @Override
    public Cita createCita(CitaDTO citaDTO, Long idPaciente, Long idMedico) throws ParseException {
        PacienteDTO pacienteDTO = pacienteService.getPacienteById(idPaciente)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con id: " + idPaciente));
        MedicoDTO medicoDTO = medicoService.getMedicoById(idMedico)
                .orElseThrow(() -> new EntityNotFoundException("Medico no encontrado con id: " + idMedico));

        citaDTO.setPacienteId(idPaciente);
        citaDTO.setMedicoId(idMedico);

        Cita cita = citaMapper.toEntity(citaDTO);
        return citaRepository.save(cita);
    }

    @Override
    public CitaDTO updateCita(Long id, CitaDTO citaDTO) throws ParseException {
        Optional<Cita> citaOptional = citaRepository.findById(id);

        if (citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fecha = dateFormat.parse(citaDTO.getFecha());


            cita.setCancelado(citaDTO.isCancelado());

            cita.setStatusCita(StatusCita.valueOf(citaDTO.getStatusCita()));
            cita.setCancelado(citaDTO.isCancelado());

            Optional<Paciente> paciente = pacienteRepository.findById(citaDTO.getPacienteId());
            Optional<Medico> medico = medicoRepository.findById(citaDTO.getMedicoId());

            cita.setPaciente(paciente.get());
            cita.setMedico(medico.get());

            return citaMapper.toDTO(citaRepository.save(cita));
        }
        return null;
    }

    @Override
    public void deleteCita(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            if (cita.getConsulta() != null) {
                Consulta consulta = cita.getConsulta();
                consulta.setCita(null);
                consultaRepository.delete(consulta);
            }
            citaRepository.delete(cita);
        }
    }

    @Override
    public List<CitaDTO> getCitasByPacienteId(Long pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<CitaDTO> getCitasByMedicoId(Long medicoId) {
        List<Cita> citas = citaRepository.findByMedicoId(medicoId);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaDTO> getCitasByStatusCita(StatusCita statusCita) {
        List<Cita> citas = citaRepository.findByStatusCita(statusCita);
        return citas.stream()
                .map(citaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
