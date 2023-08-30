package br.com.antonioalves.desafiotodolist;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.jdbc.Sql;

import br.com.antonioalves.desafiotodolist.entity.Todo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("/remove.sql")
class DesafioTodolistApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCreateTodoSuccess() {
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].name").isEqualTo(todo.getName())
				.jsonPath("$[0].description").isEqualTo(todo.getDescription())
				.jsonPath("$[0].completed").isEqualTo(todo.isCompleted())
				.jsonPath("$[0].priority").isEqualTo(todo.getPriority());
	}

	@Test
	void testCreateTodoFailure() {

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(new Todo("", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest();
	}

	@Sql("/import.sql")
	@Test
	void testUpdateTodoSuccess() {

		var todo = new Todo(9995L, "todoTeste", "Teste123TOTO9", true, 2);

		webTestClient
				.put()
				.uri("/todos/" + todo.getId())
				.bodyValue(todo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(3)
				.jsonPath("$[0].name").isEqualTo(todo.getName())
				.jsonPath("$[0].description").isEqualTo(todo.getDescription())
				.jsonPath("$[0].completed").isEqualTo(todo.isCompleted())
				.jsonPath("$[0].priority").isEqualTo(todo.getPriority());

	}

	// TestUpdateTodoFailure
	@Test
	void TestUpdateTodoFailure() {

		var unexistingId = 1L;

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(new Todo(unexistingId, "", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest();
	}

	// TestDeleteTodoSuccess
	@Sql("/import.sql")
	@Test
	void TestDeleteTodoSuccess() {
		List<Todo> todos = new ArrayList<>();
		todos.add(new Todo(9995L, "todoTeste", "Teste123", false, 1));
		todos.add(new Todo(9996L, "todoTeste2", "Teste1234", false, 1));
		todos.add(new Todo(9997L, "todoTeste3", "Teste12345", false, 1));

		webTestClient
				.delete()
				.uri("/todos/" + todos.get(2).getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(2)
				.jsonPath("$[0].name").isEqualTo(todos.get(0).getName())
				.jsonPath("$[0].description").isEqualTo(todos.get(0).getDescription())
				.jsonPath("$[0].completed").isEqualTo(todos.get(0).isCompleted())
				.jsonPath("$[0].priority").isEqualTo(todos.get(0).getPriority());
	}

	// TestDeleteTodoFailure
	@Test
	void TestDeleteTodoFailure() {

		var unexistingId = 1L;

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(new Todo(unexistingId, "", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest();
	}

	// como essa api retorna sempre uma lista de todos
	// testListTodos
	@Sql("/import.sql")
	@Test
	void testListTodos() throws Exception {

		List<Todo> todos = new ArrayList<>();
		todos.add(new Todo(9995L, "todoTeste", "Teste123", false, 1));
		todos.add(new Todo(9996L, "todoTeste2", "Teste1234", false, 1));
		todos.add(new Todo(9997L, "todoTeste3", "Teste12345", false, 1));

		webTestClient
				.get()
				.uri("/todos")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(3)
				.jsonPath("$[0].id").isEqualTo(todos.get(0).getId())
				.jsonPath("$[1].id").isEqualTo(todos.get(1).getId())
				.jsonPath("$[2].id").isEqualTo(todos.get(2).getId());
	}
}
