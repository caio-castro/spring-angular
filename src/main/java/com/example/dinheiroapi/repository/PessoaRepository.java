package com.example.dinheiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.dinheiroapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
