package org.id;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.mock.web.MockMultipartFile;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

@CrossOrigin("*")
@RestController
public class QuestionRestService {
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	FaceDetection faceDetection;

	@RequestMapping(value = "listquestions")
	public List<Question> listQuestions() {
		return questionRepository.findAll();
	}

	@GetMapping(value = "questions/{id}")
	public Question findQuestion(@PathVariable(name = "id") Long id) {
		return questionRepository.findById(id).get();
	}
	@GetMapping(value = "verifyphoto")
	public Boolean VerifyPhoto() throws IOException {
		String r=detectFace();
		System.err.println(r);
		if(r.equals("[]"))
			return false;
		return true;
	}


	@PutMapping(value = "questions/{id}")
	public Question update(@PathVariable(name = "id") Long id, @RequestBody Question q) {
		q.setId(id);
		return questionRepository.save(q);
	}

	@DeleteMapping(value = "questions/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		questionRepository.deleteById(id);
	}

	@ResponseBody
	@RequestMapping(value = "/photos/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] testphoto(@PathVariable(name = "id") Integer id) throws IOException {
		InputStream in = null;
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

	@PostMapping(value = "/upload")
	public ResponseEntity<?> uploadFile(@RequestPart(name = "description") String description1,
			@RequestPart("file") MultipartFile file1) {
		// add your logics here
		System.err.println("executeddddddddddddddddd : " + description1);

		String filePath = "";
		String contentType = file1.getContentType();
		System.out.println(contentType);

		try {
			if (!file1.isEmpty()) {

				String uploadsDir = "/uploads/";
				String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
				if (!new File(realPathtoUploads).exists()) {
					new File(realPathtoUploads).mkdir();
				}

				String[] separatedInfo = description1.split(";");
				System.out.println(separatedInfo[0] + " " + separatedInfo[1]);

				String orgName = file1.getOriginalFilename();
				filePath = "C:\\Users\\admin\\IdeaProjects\\cat-ser\\uploads\\" + orgName + ".jpg";
				String fileInformationPath = "C:\\Users\\admin\\IdeaProjects\\cat-ser\\uploads\\info_" + ".txt";

				File dest = new File(filePath);
				File dest1 = new File(fileInformationPath);
				try (PrintWriter out = new PrintWriter(fileInformationPath)) {
					out.println("Nom : " + separatedInfo[0] + "\n Adresse : " + separatedInfo[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				file1.transferTo(dest);

			}
			System.err.println(" good");
			//detectFace();
			/*
			 * try { Path path = Paths.get(filePath); String name = "file.jpg"; String
			 * originalFileName = "file.jpg";
			 * 
			 * byte[] content = null; try { content = Files.readAllBytes(path); } catch
			 * (final IOException e) { } MultipartFile result = new MockMultipartFile(name,
			 * originalFileName, contentType, content); List<FaceEntity> faceEntityList =
			 * faceDetection.detectFaceJson(result); if (faceEntityList.size() == 0)
			 * System.err.println("NoFace"); else System.err.println("Oke there are faces");
			 * } catch (IOException e1) { e1.printStackTrace(); }
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("not good");
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(null);
	}

	
	public String detectFace() throws IOException
	{
		
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec("curl -X POST http://localhost:8080/faceDetect/json -F file=@C:\\Users\\admin\\IdeaProjects\\cat-ser\\uploads\\profil.jpg");

		//Java 8 version
		String result = new BufferedReader(
		                           new InputStreamReader(pr.getInputStream()))
		                               .lines()
		                               .collect(Collectors.joining("\n"));

		System.out.println(result);
		return result;
	}
}
