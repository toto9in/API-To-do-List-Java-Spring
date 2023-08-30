package br.com.antonioalves.desafiotodolist.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.antonioalves.desafiotodolist.entity.Todo;
import br.com.antonioalves.desafiotodolist.service.TodoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    ResponseEntity<List<Todo>> create(@Valid @RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoService.create(todo));
    }

    @GetMapping
    ResponseEntity<List<Todo>> list() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(todoService.list());
    }

    @PutMapping("{id}")
    ResponseEntity<List<Todo>> update(@PathVariable Long id, @RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(todoService.update(id, todo));
    }

    @DeleteMapping("{id}")
    ResponseEntity<List<Todo>> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.delete(id));
    }
}
