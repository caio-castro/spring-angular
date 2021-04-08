package com.example.dinheiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dinheiro.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
