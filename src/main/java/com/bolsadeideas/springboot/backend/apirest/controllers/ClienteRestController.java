package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Region;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;
import com.bolsadeideas.springboot.backend.apirest.models.services.IUploadFileService;

//PARA CONFIGURAR ANGULAR
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IUploadFileService uploadService;

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		// 4 son los registros por página

		Pageable pageable = PageRequest.of(page, 4);
		return clienteService.findAll(pageable);
	}

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		// MAP INTERFAZ HASHMAP IMPLEMENTACION
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = null;

		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la bdd")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {

		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();

		/*
		 * List<String> errors = new ArrayList<>();
		 * 
		 * for (FieldError err: result.getFieldErrors()) { errors.add("El campo '" +
		 * err.getField() + "' " + err.getDefaultMessage()); }
		 * 
		 * if (result.hasErrors()) { response.put("errors", errors); return new
		 * ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST); } eslo
		 * mismo de abajo
		 */

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// PARA ACTUALIZAR
	@PutMapping("/clientes/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {

		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (clienteActual == null) {
			response.put("mensaje",
					"El cliente ID: ".concat(id.toString().concat(" no existe en la bdd, no se pudo editar")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteActual.setRegion(cliente.getRegion());

			clienteUpdated = clienteService.save(clienteActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {

			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			
			uploadService.eliminar(nombreFotoAnterior);

			clienteService.delete(id);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Cliente cliente = clienteService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = null;

			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {

				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(" ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = cliente.getFoto();

			uploadService.eliminar(nombreFotoAnterior);
			
			cliente.setFoto(nombreArchivo);

			clienteService.save(cliente);

			response.put("cliente", cliente);
			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/uploads/img/{nombreFoto:.+}") // EXPRESION REGULAR INDICANDO QUE VOY A TENER UNA EXTENSION
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		
			
		Resource recurso = null;
		
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/clientes/regiones")
	@Secured("ROLE_ADMIN")
	public List<Region> listarRegiones() {
		return clienteService.findAllRegiones();
	}

}
