package com.example.dinheiro.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.dinheiro.api.event.RecursoCriadoEvent;
import com.example.dinheiro.api.model.Categoria;
import com.example.dinheiro.api.model.Pessoa;
import com.example.dinheiro.api.repository.PessoaRepository;
import com.example.dinheiro.api.service.PessoaService;



@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa>listar(){
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);  
		
		// Se o resultado nao for um id existente , erro 404 notFound
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}

	//forma simples de fazer a inclusão vai post
	// só que não tem retorno no body do postman, somente estatus 200 ok
    //	@PostMapping
    //	public void criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
    //		 pessoaRepository.save(pessoa);
    //	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
           Pessoa pessoaSalva = pessoaService.atualizar(codigo,pessoa);
          
		 return ResponseEntity.ok(pessoaSalva);
	}
	
}
