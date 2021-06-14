package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

//PARA CONFIGURAR ANGULAR
@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		//MAP INTERFAZ HASHMAP IMPLEMENTACION
		Map<String, Object> response = new HashMap<>();		
		Cliente cliente = null;
		
		try {			
			cliente = clienteService.findById(id);			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		

		
		if (cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la bdd")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {	
		
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();	
		
		
		try {
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//PARA ACTUALIZAR
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (clienteActual == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la bdd, no se pudo editar")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}

		
		
		try {
			
			
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
						
			clienteUpdated = clienteService.save(clienteActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	

	@DeleteMapping("clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		
		Map<String, Object> response = new HashMap<>();	
		
		try {
			clienteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido eliminado con éxito!");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		

	}
}
