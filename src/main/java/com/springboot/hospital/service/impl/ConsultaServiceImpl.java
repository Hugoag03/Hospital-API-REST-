package com.springboot.hospital.service.impl;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.ConsultaMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import com.springboot.hospital.repository.ConsultaRepository;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.ConsultaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaMapper consultaMapper;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaMapper citaMapper;

    @Override
    public List<ConsultaDTO> getAllConsultas() {
        return consultaRepository.findAll().stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultaDTO> getConsultaById(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        return consulta.map(consultaMapper::toDTO);
    }

    @Override
    public ConsultaDTO createConsulta(Long citaId, ConsultaDTO consultaDTO) throws ParseException {
        CitaDTO citaDTO = citaService.getCitaById(citaId).orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        Consulta consulta = new Consulta();
        consulta.setCita(citaMapper.toEntity(citaDTO));
        consulta.setFechaConsulta(new Date());
        consulta.setInforme(consultaDTO.getInforme());

        Consulta createdConsulta = consultaRepository.save(consulta);
        return consultaMapper.toDTO(createdConsulta);
    }

    @Override
    public ConsultaDTO updateConsulta(Long id, ConsultaDTO consultaDTO) throws ParseException {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);

        if(consultaOptional.isPresent()){
            Consulta consulta = consultaOptional.get();
            consulta.setInforme(consultaDTO.getInforme());
            consulta.setFechaConsulta(consultaDTO.getFechaConsultaAsDate());

            Consulta updateConsulta = consultaRepository.save(consulta);

            Cita cita = consulta.getCita();
            if(cita != null){
                CitaDTO citaDTO = new CitaDTO();
                citaDTO.setFecha(cita.getFecha().toString());
                citaDTO.setStatusCita(cita.getStatusCita().toString());
                citaDTO.setCancelado(cita.isCancelado());
                citaDTO.setMedicoId(cita.getMedico().getId());
                citaDTO.setPacienteId(cita.getPaciente().getId());

                citaService.updateCita(cita.getId(), citaDTO);
            }

            return consultaMapper.toDTO(updateConsulta);
        }
        return null;
    }

    @Override
    public void deleteConsulta(Long id) {
        consultaRepository.deleteById(id);
    }

    @Override
    public List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm) {
        List<Consulta> consultas = consultaRepository.findByInformeContainingIgnoreCase(searchTerm);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultasByCita(Cita cita) {
        List<Consulta> consultas = consultaRepository.findByCita(cita);
        return consultas.stream()
                .map(consultaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> getConsultasByCitaId(Long citaId) throws ParseException {
        CitaDTO citaDTO = citaService.getCitaById(citaId).orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));

        Cita cita = citaMapper.toEntity(citaDTO);
        List<ConsultaDTO> consultas = getConsultasByCita(cita);
        return consultas;
    }
}
