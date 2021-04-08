package com.example.dinheiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dinheiro.api.model.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{

}
