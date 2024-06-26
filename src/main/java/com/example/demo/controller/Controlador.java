package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.modelo.Barrio;
import com.example.demo.modelo.Denuncia;
import com.example.demo.modelo.Desperfecto;
import com.example.demo.modelo.ImagenDenuncia;
import com.example.demo.modelo.ImagenReclamo;
import com.example.demo.modelo.ImagenServicioComercio;
import com.example.demo.modelo.ImagenServicioProfesional;
import com.example.demo.modelo.MovimientoDenuncia;
import com.example.demo.modelo.MovimientoReclamo;
import com.example.demo.modelo.Personal;
import com.example.demo.modelo.Reclamo;
import com.example.demo.modelo.ServicioComercio;
import com.example.demo.modelo.ServicioProfesional;
import com.example.demo.modelo.Sitio;
import com.example.demo.modelo.Vecino;
import com.example.demo.modelo.Vecinoregistrado;
import com.example.demo.repository.ImagenDenunciaRepository;
import com.example.demo.repository.ImagenReclamoRepository;
import com.example.demo.repository.ImagenServicioComercioRepository;
import com.example.demo.repository.ImagenServicioProfesionalRepository;
import com.example.demo.repository.MovimientoDenunciaRepository;
import com.example.demo.repository.ReclamoRepository;
import com.example.demo.repository.VecinoregistradoRepository;
import com.example.demo.service.BarrioService;
import com.example.demo.service.DenunciaService;
import com.example.demo.service.DesperfectoService;
import com.example.demo.service.PersonalService;
import com.example.demo.service.ReclamoService;
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

	@Autowired
	DesperfectoService desperfectoService;

	@Autowired
	ReclamoService reclamoService;

	@Autowired
	ImagenReclamoRepository imagenReclamoRepository;

	@Autowired
	ImagenDenunciaRepository imagenDenunciaRepository;

	@Autowired
	VecinoregistradoRepository vecinoregistradoRepository;

	@Autowired
	DenunciaService denunciaService;

	@Autowired
	MovimientoDenunciaRepository movimientoDenunciaRepository;

	@Autowired
	ImagenServicioProfesionalRepository imagenServicioProfesionalRepository;

	@Autowired
	ImagenServicioComercioRepository imagenServicioComercioRepository;

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

	@GetMapping("/todosLosDesperfectos")
	public List<Desperfecto> todosLosDesperfectos() {
		return desperfectoService.todosLosDesperfectos();
	}

	@GetMapping("/desperfectosPorSector")
	public List<Desperfecto> desperfectosPorSector(@RequestParam Integer legajo) {
		Personal inspector = personalservice.perfilInspector(legajo);
		if (inspector != null) {
			if (inspector.getCategoria() == 8) {
				String sectorInspector = inspector.getSector();
				return desperfectoService.desperfectoPorSector(sectorInspector);
			} else {
				System.out.println("SOS PERSONAL MUNICIPAL PERO NO INSPECTOR");
				return null;
			}
		} else {
			System.out.println("NO SOS PERSONAL MUNICIPAL");
			return null;
		}
	}

	@GetMapping("/buscarSitioId")
	public Sitio buscarSitioId(@RequestParam Integer idSitio) {
		return sitioService.buscarSitio(idSitio);
	}

	@PostMapping("/generarReclamoVecino")
	public ResponseEntity<String> generarReclamoVecino(@RequestParam String mail, @RequestParam Integer idSitio,
			@RequestParam Integer idDesperfecto, @RequestParam String descripcion,
			@RequestParam(required = false) MultipartFile[] files) {

		String resultado = reclamoService.generarReclamoVecino(mail, idSitio, idDesperfecto, descripcion, files);
//		if (resultado.equals("Reclamo generado con exito")) {
		if (resultado.contains("Tu numero de reclamo es")) {
			return ResponseEntity.ok(resultado);
		} else {
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@GetMapping("/todosReclamos")
	public List<Reclamo> todosReclamos() {
		return reclamoService.todosLosReclamos();
	}

	@GetMapping("/imagenesReclamo")
	public ResponseEntity<List<String>> obtenerImagenesReclamo(@RequestParam Integer idReclamo) {
		List<ImagenReclamo> imagenesReclamo = imagenReclamoRepository.findByIdReclamo(idReclamo);
		List<String> urls = new ArrayList<>();

		for (ImagenReclamo imagen : imagenesReclamo) {
			String url = "http://localhost:8080/imagenes/" + Paths.get(imagen.getPath()).getFileName().toString();
			urls.add(url);
		}
		return ResponseEntity.ok(urls);
	}

	@GetMapping("/imagenesDenuncia")
	public ResponseEntity<List<String>> obtenerImagenesDenuncia(@RequestParam Integer idDenuncia) {
		List<ImagenDenuncia> imagenesDenuncia = imagenDenunciaRepository.findByIdDenuncia(idDenuncia);
		List<String> urls = new ArrayList<>();
		for (ImagenDenuncia imagen : imagenesDenuncia) {
			String url = "http://localhost:8080/imagenes/" + Paths.get(imagen.getPath()).getFileName().toString();
			urls.add(url);
		}
		return ResponseEntity.ok(urls);
	}

	@GetMapping("/imagenesServicioProfesional")
	public ResponseEntity<List<String>> obtenerImagenesServicioProfesional(
			@RequestParam Integer idServicioProfesional) {
		List<ImagenServicioProfesional> imagenesServicioProf = imagenServicioProfesionalRepository
				.findByIdservicioprofesional(idServicioProfesional);
		List<String> urls = new ArrayList<>();
		for (ImagenServicioProfesional imagen : imagenesServicioProf) {
			String url = "http://localhost:8080/imagenes/" + Paths.get(imagen.getPath()).getFileName().toString();
			urls.add(url);
		}
		return ResponseEntity.ok(urls);
	}

	@GetMapping("/imagenesServicioComercio")
	public ResponseEntity<List<String>> obtenerImagenesServicioComercio(@RequestParam Integer idServicioComercio) {
		List<ImagenServicioComercio> imagenesServicioProf = imagenServicioComercioRepository
				.findByIdserviciocomercio(idServicioComercio);
		List<String> urls = new ArrayList<>();
		for (ImagenServicioComercio imagen : imagenesServicioProf) {
			String url = "http://localhost:8080/imagenes/" + Paths.get(imagen.getPath()).getFileName().toString();
			urls.add(url);
		}
		return ResponseEntity.ok(urls);
	}

	@GetMapping("/misReclamosVecino")
	public List<Reclamo> misReclamosVecino(@RequestParam String mail) {
		return reclamoService.misReclamosVecino(mail);
	}

	@GetMapping("/movimientosReclamo")
	public List<MovimientoReclamo> movimientosReclamo(@RequestParam Integer idReclamo) {
		return reclamoService.movimientosReclamo(idReclamo);
	}

	@GetMapping("/movimientosDenuncia")
	public List<MovimientoDenuncia> movimientosDenun(@RequestParam Integer idDenuncia) {
		return movimientoDenunciaRepository.findAll();
	}

	@PostMapping("/crearServicioProfesional")
	public String generarServicioProfesional(@RequestParam String mail, @RequestParam String medioContacto,
			@RequestParam String horario, @RequestParam String rubro, @RequestParam String descripcion,
			@RequestParam(required = false) MultipartFile[] files) {

		return profesionalservice.crearServicioProfesional(mail, medioContacto, horario, rubro, descripcion, files);
	}

	@PostMapping("/crearServicioComercio")
	public String generarServicioComercio(@RequestParam String mail, @RequestParam String direccion,
			@RequestParam String contacto, @RequestParam String descripcion,
			@RequestParam(required = false) MultipartFile[] files) {
		return comercioservice.crearServicioComercio(mail, direccion, contacto, descripcion, files);
	}

	@GetMapping("/misServiciosProfesionales")
	public List<ServicioProfesional> misServicioProfesionales(@RequestParam String mail) {
		Vecino vecino = vecinoservice.perfilVecinoregistrado(mail);
		Vecinoregistrado vecinoregistrado = vecinoregistradoRepository.findById(vecino.getDocumento()).get();
		List<ServicioProfesional> servicios = profesionalservice.serviciosProfesionales();
		List<ServicioProfesional> misServicio = new ArrayList<>();
		for (ServicioProfesional servicio : servicios) {
			if (servicio.getVecino().getDocumento().equals(vecinoregistrado.getDocumento())) {
				misServicio.add(servicio);
			}
		}
		return misServicio;
	}

	@GetMapping("/misServiciosComercio")
	public List<ServicioComercio> misServicioComercio(@RequestParam String mail) {
		Vecino vecino = vecinoservice.perfilVecinoregistrado(mail);
		Vecinoregistrado vecinoregistrado = vecinoregistradoRepository.findById(vecino.getDocumento()).get();
		List<ServicioComercio> servicios = comercioservice.comercios();
		List<ServicioComercio> misServicio = new ArrayList<>();
		for (ServicioComercio servicio : servicios) {
			if (servicio.getVecino().getDocumento().equals(vecinoregistrado.getDocumento())) {
				misServicio.add(servicio);
			}
		}
		return misServicio;
	}

	@DeleteMapping("/eliminarServicioComercio")
	public ResponseEntity<String> eliminarServicioComercio(@RequestParam String mail,
			@RequestParam Integer idServicio) {
		String resultado = comercioservice.eliminarServicioComercio(mail, idServicio);
		if (resultado.equals("Servicio Comercio eliminado")) {
			return ResponseEntity.ok(resultado);
		} else {
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@DeleteMapping("/eliminarServicioProfesional")
	public ResponseEntity<String> eliminarServicioProfesional(@RequestParam String mail,
			@RequestParam Integer idServicio) {
		String resultado = profesionalservice.eliminarServicioProfesional(mail, idServicio);
		if (resultado.equals("Servicio Profesional eliminado")) {
			return ResponseEntity.ok(resultado);
		} else {
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@PostMapping("/crearDenuncia")
	public ResponseEntity<String> crearDenuncia(@RequestParam String mail, @RequestParam String dniDenunciado,
			@RequestParam Integer idSitio, @RequestParam String descripcion,
			@RequestParam(required = false) MultipartFile[] files) {
		String resultado = denunciaService.realizarDenuncia(mail, dniDenunciado, idSitio, descripcion, files);
		if (resultado.contains("Tu numero de denuncia es")) {
			return ResponseEntity.ok(resultado);
		} else {
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@GetMapping("/denunciasRealizadas")
	public List<Denuncia> misDenuncias(@RequestParam String mail) {
		return denunciaService.denunciasRealizadas(mail);
	}

	@GetMapping("/denunciasRecibidas")
	public List<Denuncia> denunciasRecibidas(@RequestParam String mail) {
		return denunciaService.denunciasRecibidas(mail);
	}

	@PostMapping("/generarReclamoInspector")
	public ResponseEntity<String> generarReclamoInspector(@RequestParam Integer legajo, @RequestParam Integer idSitio,
			@RequestParam Integer idDesperfecto, @RequestParam String descripcion,
			@RequestParam(required = false) MultipartFile[] files) {
		String resultado = reclamoService.generarReclamoInspector(legajo, idSitio, idDesperfecto, descripcion, files);
		if (resultado.contains("Tu numero de reclamo es")) {
			return ResponseEntity.ok(resultado);
		} else {
			return ResponseEntity.status(400).body(resultado);
		}
	}

	@GetMapping("/reclamosPorSector")
	public List<Reclamo> reclamosDelSector(@RequestParam Integer legajo) {
		return reclamoService.reclamosDelSector(legajo);
	}

	@GetMapping("/misReclamosInspector")
	public List<Reclamo> misReclamosInspec(@RequestParam Integer legajo) {
		return reclamoService.misReclamosInspector(legajo);
	}

	@GetMapping("/reclamoPorId")
	public Reclamo buscarReclamoPorId(@RequestParam Integer idReclamo) {
		return reclamoService.reclamoPorId(idReclamo);
	}

	@GetMapping("/reclamoPorIdUnificado")
	public List<Reclamo> buscarReclamoPorIdUnificado(@RequestParam Integer idReclamo) {
		return reclamoService.reclamosPorIdUnificado(idReclamo);
	}

}
