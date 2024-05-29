package com.springboot.hospital.repository;

import com.springboot.hospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Medico findByNombre(String nombre);

    List<Medico> findByEspecialidad(String especialidad);
}
