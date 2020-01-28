package org.id;

import java.io.IOException;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(configuration = FeignSimpleEncoderConfig.class,name = "FaceDetection", url = "http://localhost:8080")
public interface FaceDetection {
	@ResponseBody
	@PostMapping(value = "/faceDetect/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// @RequestMapping(value = "/faceDetect/json",headers=("content-type=
	// multipart/form-data;boundary="), method = RequestMethod.POST)
	public List<FaceEntity> detectFaceJson(@PathVariable("file") MultipartFile file) throws IOException;
}