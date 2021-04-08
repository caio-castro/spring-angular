package com.example.dinheiro.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dinheiro.api.event.RecursoCriadoEvent;
import com.example.dinheiro.api.model.Categoria;
import com.example.dinheiro.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaResource {
	
	@Autowired
	private  ApplicationEventPublisher publisher;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();	
	}
	
	@PostMapping
	// Colocando o status 201 quando é feita uma inclusão post
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		 Categoria categoriaSalva = categoriaRepository.save(categoria);
		 
		 publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);  
		
		// Se o resultado não for um id existentem , noyFound
		return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}

	
}
