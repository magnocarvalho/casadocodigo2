package br.com.casadocodigo.loja.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component
public class FileSaver {
	
	@Autowired
	private AmazonS3Client s3;
	
	public String write(String baseFolder, MultipartFile multipartFile) {
		
		try {
			s3.putObject("casadocodigo2", multipartFile.getOriginalFilename(), multipartFile.getInputStream(), new ObjectMetadata());
			
			//url de acesso ao arquivo
			return "https://s3.amazonaws.com/casadocodigo2/" + multipartFile.getOriginalFilename() + "?noAuth=true";
		} catch (AmazonClientException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
//	private AmazonS3Client client() {
//		AWSCredentials credentials = new BasicAWSCredentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
//		AmazonS3Client newClient = new AmazonS3Client(credentials, new ClientConfiguration());
//		newClient.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
//		newClient.setEndpoint("http://localhost:9444/s3");
//		return newClient;
//	}
	
}
