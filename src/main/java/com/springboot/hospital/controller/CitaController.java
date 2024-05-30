package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.StatusCita;
import com.springboot.hospital.service.CitaService;
import com.springboot.hospital.service.MedicoService;
import com.springboot.hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarCitas(){
        return new ResponseEntity<>(citaService.getAllCitas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> listarCitaPorId(@PathVariable Long id){
        Optional<CitaDTO> citaDTOOptional = citaService.getCitaById(id);

        return citaDTOOptional.map(cita -> new ResponseEntity<>(cita, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{pacienteId}/{medicoId}")
    public ResponseEntity<CitaDTO> guardarCita(@RequestBody CitaDTO citaDTO, @PathVariable Long pacienteId, @PathVariable Long medicoId) throws ParseException {
        Cita newCita = citaService.createCita(citaDTO, pacienteId, medicoId);

        if(newCita == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CitaDTO newCitaDTO = citaMapper.toDTO(newCita);
        return new ResponseEntity<>(newCitaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizarCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) throws ParseException {
        CitaDTO citaUpdate = citaService.updateCita(id, citaDTO);

        if(citaUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(citaUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id){
        citaService.deleteCita(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDTO>> listarCitasPorPacienteId(@PathVariable Long pacienteId){

        return new ResponseEntity<>(citaService.getCitasByPacienteId(pacienteId), HttpStatus.OK);
    }

    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaDTO>> listarCitasPorMedicoId(@PathVariable Long medicoId){

        return new ResponseEntity<>(citaService.getCitasByMedicoId(medicoId), HttpStatus.OK);
    }

    @GetMapping("/status/{statusCita}")
    public ResponseEntity<List<CitaDTO>> listarCitasPorStatus(@PathVariable StatusCita statusCita){

        return new ResponseEntity<>(citaService.getCitasByStatusCita(statusCita), HttpStatus.OK);
    }


}
