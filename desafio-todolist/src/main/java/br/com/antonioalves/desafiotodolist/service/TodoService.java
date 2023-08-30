package br.com.antonioalves.desafiotodolist.service;

import br.com.antonioalves.desafiotodolist.entity.Todo;
import br.com.antonioalves.desafiotodolist.exception.BadRequestException;
import br.com.antonioalves.desafiotodolist.repository.TodoRepository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
  private TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  public List<Todo> list() {
    Sort sort = Sort.by(Direction.DESC, "priority")
        .and(Sort.by(Direction.ASC, "id"));

    return todoRepository.findAll(sort);
  }

  public List<Todo> create(Todo todo) {
    todoRepository.save(todo);
    return list();
  }

  public List<Todo> update(Long id, Todo todo) {
    todoRepository.findById(id).ifPresentOrElse((existingTodo) -> {
      todo.setId(id);
      todoRepository.save(todo);
    }, () -> {
      throw new BadRequestException("Todo %d não existe! ".formatted(id));
    });

    return list();

  }

  public List<Todo> delete(Long id) {
    todoRepository.findById(id).ifPresentOrElse((existingTodo) -> todoRepository.delete(existingTodo), () -> {
      throw new BadRequestException("Todo %d não existe!".formatted(id));
    });

    return list();
  }

}
