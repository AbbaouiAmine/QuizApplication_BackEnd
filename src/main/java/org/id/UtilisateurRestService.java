package org.id;

import java.util.List;

import org.id.dao.UtilisateurRepository;
import org.id.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UtilisateurRestService {
	@Autowired
	UtilisateurRepository utilisateurRepository;

	@RequestMapping(value = "listutilisateurs")
	public List<Utilisateur> listUtilisateurs() {
		return utilisateurRepository.findAll();
	}

	@GetMapping(value = "utilisateurs/{id}")
	public Utilisateur findUtilisateur(@PathVariable(name = "id") Long id) {
		return utilisateurRepository.findById(id).get();
	}

	@PutMapping(value = "utilisateurs/{id}")
	public Utilisateur update(@PathVariable(name = "id") Long id, @RequestBody Utilisateur q) {
		q.setId(id);
		return utilisateurRepository.save(q);
	}

	@DeleteMapping(value = "Utilisateurs/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		utilisateurRepository.deleteById(id);
	}
}
