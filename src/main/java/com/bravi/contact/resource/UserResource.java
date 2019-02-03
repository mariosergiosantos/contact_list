package com.bravi.contact.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bravi.contact.model.Contacts;
import com.bravi.contact.model.User;
import com.bravi.contact.repository.ContactRepository;
import com.bravi.contact.repository.UserRepository;

@RestController
@RequestMapping("api/users")
public class UserResource {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;

	@PostMapping
	public User adicionar(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping
	public List<User> listar() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> buscar(@PathVariable Long id) {
		User user = userRepository.findOne(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> atualizar(@PathVariable Long id, @Valid @RequestBody User contato) {
		User current_user = userRepository.findOne(id);

		if (current_user == null) {
			return ResponseEntity.notFound().build();
		}

		current_user.setName(contato.getName());
		current_user = userRepository.save(current_user);

		return ResponseEntity.ok(current_user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		User user = userRepository.findOne(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		userRepository.delete(user);

		return ResponseEntity.noContent().build();
	}

	// Contatos

	@PostMapping("/{id}/contact")
	public ResponseEntity<Contacts> adicionarContatos(@PathVariable Long id, @RequestBody Contacts userRequest) {
		User user = userRepository.findOne(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		userRequest.setUser(user);

		return ResponseEntity.ok(contactRepository.save(userRequest));

	}

	@PutMapping("/{id}/contact/{idContato}")
	public ResponseEntity<Contacts> atualizarContato(@PathVariable Long id, @PathVariable Long idContato,
			@Valid @RequestBody Contacts contato) {
		User current_user = userRepository.findOne(id);

		if (current_user == null) {
			return ResponseEntity.notFound().build();
		}

		contato.setUser(current_user);
		contato.setId(idContato);

		return ResponseEntity.ok(contactRepository.save(contato));
	}

	@DeleteMapping("/{id}/contact/{idContato}")
	public ResponseEntity<Void> removerContato(@PathVariable Long id, @PathVariable Long idContato) {
		User user = userRepository.findOne(id);
		Contacts contact = contactRepository.findOne(idContato);

		if (user == null || contact == null) {
			return ResponseEntity.notFound().build();
		}

		contactRepository.delete(contact);

		return ResponseEntity.noContent().build();
	}

}
