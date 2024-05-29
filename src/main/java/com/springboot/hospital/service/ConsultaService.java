package com.springboot.hospital.service;

import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.model.Cita;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ConsultaService {

    List<ConsultaDTO> getAllConsultas();

    Optional<ConsultaDTO> getConsultaById(Long id) ;

    ConsultaDTO createConsulta(Long citaId, ConsultaDTO consultaDTO) throws ParseException;

    ConsultaDTO updateConsulta(Long id, ConsultaDTO consultaDTO) throws ParseException;

    void deleteConsulta(Long id);

    List<ConsultaDTO> getConsultasByInformeContaining(String searchTerm);

    List<ConsultaDTO> getConsultasByCita(Cita cita);

    List<ConsultaDTO> getConsultasByCitaId(Long citaId) throws ParseException;
}
