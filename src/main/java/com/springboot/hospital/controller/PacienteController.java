package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.PacienteDTO;
import com.springboot.hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listarPacientes(){
        List<PacienteDTO> pacientes = pacienteService.getAllPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> listarPacientePorId(@PathVariable Long id){
        return pacienteService.getPacienteById(id)
                .map(paciente -> new ResponseEntity<>(paciente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> guardarPaciente(@RequestBody PacienteDTO pacienteDTO){
        PacienteDTO createdPacienteDTO = pacienteService.createPaciente(pacienteDTO);
        return new ResponseEntity<>(createdPacienteDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO){
        PacienteDTO updatePacienteDTO = pacienteService.updatePaciente(id, pacienteDTO);
        if (updatePacienteDTO != null){
            return new ResponseEntity<>(updatePacienteDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        pacienteService.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/citas")
    public ResponseEntity<Collection<CitaDTO>> listarCitasPorPacienteId(@PathVariable Long id){
        Collection<CitaDTO> citas = pacienteService.getCitasByPacienteId(id);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
}
