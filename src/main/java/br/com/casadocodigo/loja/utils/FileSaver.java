package br.com.casadocodigo.loja.utils;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

@Component
public class FileSaver {
	
	@Autowired
	private AmazonS3Client s3;
	
	public String write(MultipartFile multipartFile) {
		
		try {
			s3.putObject("casadocodigo2", multipartFile.getOriginalFilename(), multipartFile.getInputStream(), new ObjectMetadata());
			
			//url de acesso ao arquivo
			//return "https://s3.amazonaws.com/casadocodigo2/" + multipartFile.getOriginalFilename() + "?noAuth=true";
			
			return multipartFile.getOriginalFilename();
			
		} catch (AmazonClientException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public InputStream read(String key){
		S3Object object = s3.getObject("casadocodigo2", key);
        InputStream inputStream = object.getObjectContent();
        return inputStream;
	}
	
//	private AmazonS3Client client() {
//		AWSCredentials credentials = new BasicAWSCredentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
//		AmazonS3Client newClient = new AmazonS3Client(credentials, new ClientConfiguration());
//		newClient.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
//		newClient.setEndpoint("http://localhost:9444/s3");
//		return newClient;
//	}
	
}
