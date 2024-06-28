package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Barrio;
import com.example.demo.modelo.Personal;
import com.example.demo.modelo.ServicioComercio;
import com.example.demo.modelo.ServicioProfesional;
import com.example.demo.modelo.Sitio;
import com.example.demo.modelo.Vecino;
import com.example.demo.service.BarrioService;
import com.example.demo.service.PersonalService;
import com.example.demo.service.ServicioComercioService;
import com.example.demo.service.ServicioProfesionalService;
import com.example.demo.service.SitioService;
import com.example.demo.service.VecinoService;

@RestController
@RequestMapping("/inicio")
public class Controlador {

	@Autowired
	PersonalService personalservice;

	@Autowired
	VecinoService vecinoservice;

	@Autowired
	ServicioProfesionalService profesionalservice;

	@Autowired
	ServicioComercioService comercioservice;

	@Autowired
	BarrioService barrioService;

	@Autowired
	SitioService sitioService;

	@PostMapping("/loginInspector")
	public ResponseEntity<String> loginInspector(@RequestParam Integer legajo, @RequestParam String password) {
		boolean resultado = personalservice.loginInspector(legajo, password);
		if (resultado == true) {
			System.out.println("ACCION --> LOGIN INSPECTOR: Resultado Exitoso");
			return ResponseEntity.ok("Login exitoso");
		} else {
			System.out.println("ACCION --> LOGIN INSPECTOR: Resultado Fallido");
			return ResponseEntity.status(401).body("Datos incorrectos");
		}
	}

	@GetMapping("/inspectores")
	public List<Personal> inspectores() {
		System.out.println("ACCION --> DEVOLVIENDO LISTA DE INSPECTORES");
		return personalservice.inspectores();
	}

	@PutMapping("/cambiarPassword")
	public ResponseEntity<String> cambiarPassword(@RequestParam Integer legajo, @RequestParam String passwordActual,
			@RequestParam String passwordNueva, @RequestParam String passwordNueva2) {
		boolean resultado = personalservice.cambiarPassword(legajo, passwordActual, passwordNueva, passwordNueva2);
		if (resultado) {
			System.out.println("ACCION --> CAMBIAR PASSWORD: Resultado Exitoso");
			return ResponseEntity.ok("Cambio de contraseña exitoso");
		} else {
			System.out.println("ACCION --> CAMBIAR PASSWORD: Resultado Fallido");
			return ResponseEntity.status(400).body("Error al cambiar la contraseña");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam String documento, @RequestParam String mail) {
		String resultado = vecinoservice.register2(documento, mail);
		if (resultado.equals("Registro exitoso")) {
			System.out.println("ACCION --> REGISTER VECINO: Resultado Exitoso");
			return ResponseEntity.ok(resultado);
		} else {
			System.out.println("ACCION --> REGISTER VECINO: Resultado Fallido");
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@PostMapping("/loginVecino")
	public ResponseEntity<String> loginVecino(@RequestParam String mail, @RequestParam String contrasenia) {
		String resultado = vecinoservice.login(mail, contrasenia);
		if (resultado.equals("Ingreso exitoso")) {
			System.out.println("ACCION --> LOGIN VECINO: Resultado Exitoso");
			return ResponseEntity.ok(resultado);
		} else {
			System.out.println("ACCION --> LOGIN VECINO: Resultado Fallido");
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@GetMapping("/servicios/profesionales")
	public List<ServicioProfesional> serviciosProfesionales() {
		System.out.println("ACCION --> DEVOLVIENDO LISTA SERVICIOS PROFESIONALES");
		return profesionalservice.serviciosProfesionalesHabilitados();
	}

	@GetMapping("/servicios/comercios")
	public List<ServicioComercio> servicioComercios() {
		System.out.println("ACCION --> DEVOLVIENDO LISTA SERVICIOS COMERCIOS");
		return comercioservice.serviciosComerciosHabilitados();
	}

	@PostMapping("/vecino/olvidecontrasenia")
	public ResponseEntity<String> olvideContrasenia(@RequestParam String mail) {
		String resultado = vecinoservice.olvideContrasenia(mail);
		if (resultado.equals("Correo enviado correctamente")) {
			System.out.println("ACCION --> OLVIDE CONTRASENIA VECINO: Resultado Exitoso ");
			return ResponseEntity.ok(resultado);
		} else {
			System.out.println("ACCION --> OLVIDE CONTRASENIA VECINO: Resultado Fallido ");
			return ResponseEntity.status(400).body(resultado);
		}
	}

//		NUEVOOOOOOOOO: Despues entrega
	@GetMapping("/perfilInspector")
	public ResponseEntity<Personal> perfilInspector(@RequestParam Integer legajo) {
		Personal inspector = personalservice.perfilInspector(legajo);
		if (inspector != null) {
			System.out.println("ACCION --> PERFIL INSPECTOR: Resultado Exitoso " + inspector);
			return ResponseEntity.ok(inspector);
		} else {
			System.out.println("ACCION --> PERFIL INSPECTOR: Resultado Fallido ");
			return ResponseEntity.status(404).body(null);
		}
	}

//		NUEVOOOOOOOOO: Despues entrega
	@GetMapping("/perfilVecino")
	public ResponseEntity<Vecino> perfilVecino(@RequestParam String mail) {
		Vecino vecino = vecinoservice.perfilVecinoregistrado(mail);
		if (vecino != null) {
			System.out.println("ACCION --> PERFIL VECINO: Resultado Exitoso " + vecino);
			return ResponseEntity.ok(vecino);
		} else {
			System.out.println("ACCION --> PERFIL VECINO: Resultado Fallido ");
			return ResponseEntity.status(404).body(null);
		}
	}

	@GetMapping("/buscarBarrioId")
	public ResponseEntity<Barrio> buscarBarrio(@RequestParam Integer idBarrio) {
		Barrio barrio = barrioService.barrioId(idBarrio);
		if (barrio != null) {
			System.out.println("ACCION --> BUSCAR BARRIO: Resultado Exitoso");
			System.out.println("BARRIO ---> " + barrio);
			return ResponseEntity.ok(barrio);
		} else {
			System.out.println("ACCION --> BUSCAR BARRIO: Resultado Fallido (Barrio no encontrado)");
			return ResponseEntity.status(404).body(null);
		}
	}

//	ESTO ES NUEVO
	@PutMapping("/cambiarContraseniaVecino")
	public ResponseEntity<String> cambiarContraseniaVecino(@RequestParam String mail, @RequestParam String actual,
			@RequestParam String nueva1, @RequestParam String nueva2) {

		String resultado = vecinoservice.cambiarContraseniaVecino(mail, actual, nueva1, nueva2);
		if (resultado.equals("CAMBIO DE CONTRASENIA EXITOSO")) {
			return ResponseEntity.ok(resultado);
		} else {
			return ResponseEntity.status(404).body(resultado);
		}

	}

	@GetMapping("/sitios")
	public List<Sitio> sitios() {
		return sitioService.sitios();
	}

//	@PutMapping("/cambiarContraseniaVecino")

}
