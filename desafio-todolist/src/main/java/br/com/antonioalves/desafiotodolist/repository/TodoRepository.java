package br.com.antonioalves.desafiotodolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.antonioalves.desafiotodolist.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    
}
