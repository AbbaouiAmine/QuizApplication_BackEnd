package org.id;

import java.util.List;


import org.id.dao.ReponseRepository;
import org.id.entities.Reponse;
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
public class ReponseRestService {
	@Autowired
    ReponseRepository reponseRepository;

    @RequestMapping(value = "listreponses")
    public List<Reponse>  listreponses()
    {
        return  reponseRepository.findAll();
    }
    @GetMapping(value = "reponses/{id}")
    public Reponse findReponse(@PathVariable(name = "id") Long id)
    {
        return  reponseRepository.findById(id).get();
    }

    @PutMapping(value = "reponses/{id}")
    public Reponse update(@PathVariable(name = "id") Long id,@RequestBody Reponse r)
    {
        r.setId(id);
        return  reponseRepository.save(r);
    }

    @DeleteMapping(value = "reponses/{id}")
    public void delete(@PathVariable(name = "id") Long id)
    {
          reponseRepository.deleteById(id);
    }
    
}
