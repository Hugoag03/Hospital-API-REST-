package com.springboot.hospital.repository;

import com.springboot.hospital.model.Cita;
import com.springboot.hospital.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByCita(Cita cita);

    List<Consulta> findByInformeContainingIgnoreCase(String searchTerm);
}
