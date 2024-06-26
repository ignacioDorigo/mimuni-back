package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.ServicioComercio;

public interface ServicioComercioRepository extends JpaRepository<ServicioComercio, Integer> {

	public List<ServicioComercio> findByEstado(String estado);
}
