package com.example.dinheiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dinheiroapi.model.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{

}
