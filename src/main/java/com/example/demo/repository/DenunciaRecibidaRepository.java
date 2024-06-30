package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.DenunciaRecibida;

@Repository
public interface DenunciaRecibidaRepository extends JpaRepository<DenunciaRecibida, Integer> {

}
