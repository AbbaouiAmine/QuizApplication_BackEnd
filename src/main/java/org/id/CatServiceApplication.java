package org.id;

import org.id.dao.QuestionRepository;
import org.id.dao.ReponseRepository;
import org.id.dao.UtilisateurRepository;
import org.id.entities.Question;
import org.id.entities.Reponse;
import org.id.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
@EnableFeignClients
public class CatServiceApplication implements CommandLineRunner {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ReponseRepository reponseRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(CatServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(Question.class);

		Question q1 = new Question(null, "photos/1");
		Reponse q1_r1 = new Reponse(null, "1 - مركز الشرطة.", false);
		Reponse q1_r2 = new Reponse(null, "2 - موقف السيارات.", true);

		questionRepository.save(q1);
		reponseRepository.save(q1_r1);
		reponseRepository.save(q1_r2);

		questionRepository.save(new Question(null, "photos/2"));
		Reponse q2_r1 = new Reponse(null, "1 - نعم.", false);
		Reponse q2_r2 = new Reponse(null, "2 - لا.", true);

		questionRepository.save(new Question(null, "photos/3"));
		Reponse q3_r1 = new Reponse(null, "1 - نسمح لي جاين من ليمن و ندوز.", true);
		Reponse q3_r2 = new Reponse(null, "2 - ندوز.", false);

		questionRepository.save(new Question(null, "photos/4"));
		Reponse q4_r1 = new Reponse(null, "1 - يمكن ليا نزيد فسرعة ديالي.", false);
		Reponse q4_r2 = new Reponse(null, "2 - نشد ليمن مزيان.", true);

		utilisateurRepository.save(new Utilisateur(null, "admin", "admin", "name", "email"));

		// produitRepository.findAll().forEach(p->{System.out.println(p.toString());});
	}
}
