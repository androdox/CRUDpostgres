package com.example.demo.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.UsuarioService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user/")
public class UsuarioRest {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	private ResponseEntity<List<Usuario>> getAllUsuarios() {
		return ResponseEntity.ok(usuarioService.findAll());

	}
	@PostMapping
	private ResponseEntity<Usuario> saveUsusario(@RequestBody Usuario usuario){
		try {
			Usuario usuarioGuardado = usuarioService.save(usuario);
			return ResponseEntity.created(new URI("/user/"+usuarioGuardado.getId())).body(usuarioGuardado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@GetMapping (path = {"{id}"})
	private ResponseEntity<Optional<Usuario>> getUsuarioId(@PathVariable("id") int id){
		return ResponseEntity.ok(usuarioService.findById(Long.valueOf(id)));
	}
	
	@PutMapping (path = {"{id}"})
	private ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable("id") int id){
		usuario.setId(Long.valueOf(id));
		try {
			Usuario usuarioGuardado = usuarioService.save(usuario);
			return ResponseEntity.created(new URI("/user/"+usuarioGuardado.getId())).body(usuarioGuardado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping (path = {"{id}"})
	private ResponseEntity<Long> deleteUsuario(@PathVariable Long id) {

        try {
        	usuarioService.deleteById(id);
        	return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
    }
	
}
