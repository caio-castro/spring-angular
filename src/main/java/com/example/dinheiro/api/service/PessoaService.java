package com.example.dinheiro.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.dinheiro.api.model.Pessoa;
import com.example.dinheiro.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo,Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo)
				   .orElseThrow(()-> new EmptyResultDataAccessException(1));
			        //.orElseThrow equivalente ao código
//			         if(pessoaSalva == null) {
//			        	 throw new EmptyResultDataAccessException(1);
//			         }
			
			BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
	    return pessoaRepository.save(pessoaSalva);
	}
	
}
