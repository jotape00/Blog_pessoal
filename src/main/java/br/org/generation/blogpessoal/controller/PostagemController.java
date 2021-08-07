package br.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.Postagem;
import br.org.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens") 
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class PostagemController {
	
	@Autowired 
	private PostagemRepository postagemRepository;
	
	@GetMapping
	private ResponseEntity<List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll()); // OK = 200
	}
		
	@GetMapping("idifelse/{id}")
	public ResponseEntity<Postagem> getByIdIfElse(@PathVariable long id) {

		Optional<Postagem> postagem = postagemRepository.findById(id);
		if (postagem.isPresent()) {
			return ResponseEntity.ok(postagem.get());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/idtrycatch/{id}")
	public ResponseEntity<Postagem> getByIdTryCatch(@PathVariable long id) {

		Optional<Postagem> postagem = postagemRepository.findById(id);
		try {
			return ResponseEntity.ok(postagem.get());
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return postagemRepository.findById(id)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
	}

	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		postagemRepository.deleteById(id);
	}	

}