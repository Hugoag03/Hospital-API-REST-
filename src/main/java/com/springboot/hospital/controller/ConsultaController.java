package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CitaDTO;
import com.springboot.hospital.dto.ConsultaDTO;
import com.springboot.hospital.model.Cita;
import com.springboot.hospital.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas(){
        List<ConsultaDTO> consultas = consultaService.getAllConsultas();
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> listarConsultaPorId(@PathVariable Long id){
        return consultaService.getConsultaById(id)
                .map(consulta -> new ResponseEntity<>(consulta, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{citaId}")
    public ResponseEntity<ConsultaDTO> guardarConsulta(@PathVariable Long citaId, @RequestBody ConsultaDTO consultaDTO) throws ParseException {
        ConsultaDTO createdConsultaDTO = consultaService.createConsulta(citaId, consultaDTO);
        if(createdConsultaDTO != null){
            return new ResponseEntity<>(createdConsultaDTO, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> actualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) throws ParseException {
        ConsultaDTO updatedConsultaDTO = consultaService.updateConsulta(id, consultaDTO);

        if(updatedConsultaDTO != null){
            return new ResponseEntity<>(updatedConsultaDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConsulta(@PathVariable Long id){
        consultaService.deleteConsulta(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{searchTerm}/informe")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasCuyoInformeContenga(@PathVariable String searchTerm){
        List<ConsultaDTO> consultas = consultaService.getConsultasByInformeContaining(searchTerm);
        if (consultas != null){
            return new ResponseEntity<>(consultas, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cita")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasPorCita(@RequestBody Cita cita){
        List<ConsultaDTO> consultas = consultaService.getConsultasByCita(cita);

        if(consultas != null){
            return new ResponseEntity<>(consultas, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{citaId}/cita")
    public ResponseEntity<List<ConsultaDTO>> listarConsultasPorCitaId(@PathVariable Long citaId) throws ParseException {
        List<ConsultaDTO> consultas = consultaService.getConsultasByCitaId(citaId);

        if(consultas != null){
            return new ResponseEntity<>(consultas, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
