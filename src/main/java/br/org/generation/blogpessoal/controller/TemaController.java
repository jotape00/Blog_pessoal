package br.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.blogpessoal.model.Tema;
import br.org.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas") 
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class TemaController {
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	private ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable long id) {
		return temaRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Tema> postTema(@RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Tema> putTema(@RequestBody Tema tema) {
		Optional<Tema> temaUpdate = temaRepository.findById(tema.getId());
		
		if (temaUpdate.isPresent() ) {
			return ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(tema));
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado!", null);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteTema(@PathVariable long id) {
		Optional<Tema> tema = temaRepository.findById(id);
		
		if (tema.isPresent()) {
			temaRepository.deleteById(id);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema não encontrado!", null);
		}
		
	}
}