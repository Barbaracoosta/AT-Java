package at.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.api.model.Pessoa;
import at.api.service.MockService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private MockService mockService;

	@GetMapping
	public ResponseEntity<List<Pessoa>> get(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer idade) {
		return new ResponseEntity<>(mockService.consultarPessoas(nome, idade), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> post(@RequestBody Pessoa body) {

		try {
			mockService.adicionarPessoa(body);
			return new ResponseEntity<>("Pessoa adicionada!", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<List<Pessoa>> put(@RequestBody Pessoa pessoa) {
		List<Pessoa> pessoas = mockService.atualizarPiadas(pessoa);
		return new ResponseEntity<>(pessoas, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam(required = false) String nome) {
		return new ResponseEntity<>(nome, HttpStatus.OK);
	}

}