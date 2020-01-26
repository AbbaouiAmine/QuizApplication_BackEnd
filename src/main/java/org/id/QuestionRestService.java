package org.id;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.id.dao.QuestionRepository;
import org.id.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

@CrossOrigin("*")
@RestController
public class QuestionRestService {
	@Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "listquestions")
    public List<Question>  listQuestions()
    {
        return  questionRepository.findAll();
    }

    
    @GetMapping(value = "questions/{id}")
    public Question findQuestion(@PathVariable(name = "id") Long id)
    {
        return  questionRepository.findById(id).get();
    }

    @PutMapping(value = "questions/{id}")
    public Question update(@PathVariable(name = "id") Long id,@RequestBody Question q)
    {
        q.setId(id);
        return  questionRepository.save(q);
    }

    @DeleteMapping(value = "questions/{id}")
    public void delete(@PathVariable(name = "id") Long id)
    {
          questionRepository.deleteById(id);
    }
    
	
	  @ResponseBody
	  @RequestMapping(value = "/photos/{id}", method = RequestMethod.GET, produces =
	  MediaType.IMAGE_JPEG_VALUE) 
	  public byte[] testphoto(@PathVariable(name = "id") Integer id) throws IOException 
	  { 
		  InputStream in=null;
		 switch (id) {
		case 1:
			 in = new ClassPathResource("/static/q1.jpg").getInputStream();
			break;
		case 2:
			 in = new ClassPathResource("/static/q2.jpg").getInputStream();
			break;
		case 3:
			 in = new ClassPathResource("/static/q3.jpg").getInputStream();
			break;
		case 4:
			 in = new ClassPathResource("/static/q4.jpg").getInputStream();
			break;

		default:
			break;
		}
		 
		 
	  
	  return IOUtils.toByteArray(in); 
	  }
	  
	  @Bean
	    public MultipartConfigElement multipartConfigElement() {
	        return new MultipartConfigElement("");
	    }

	    @Bean
	    public MultipartResolver multipartResolver() {
	        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
	        multipartResolver.setMaxUploadSize(1000000);
	        return multipartResolver;
	    }
	  @PostMapping(value = "/upload",consumes = "multipart/form-data")
	  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	              //add your logics here
		  System.err.println("executeddddddddddddddddd");
	              File newFile = new File(file.getOriginalFilename());
	              try {
					newFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	              return ResponseEntity.ok().body(null);
	  }
}
