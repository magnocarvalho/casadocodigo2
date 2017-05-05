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
	
	private String bucketName = "casadocodigo2";
	
	@Autowired
	private AmazonS3Client s3;
	
	/**
	 * Efetua a gravação do arquivo no servidor
	 * @param multipartFile MultipartFile
	 * @return
	 */
	public String write(MultipartFile multipartFile) {
		
		try {
			if(multipartFile == null || multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().length() == 0)return null;
			
			s3.putObject(bucketName, multipartFile.getOriginalFilename(), multipartFile.getInputStream(), new ObjectMetadata());
			
			//url de acesso ao arquivo
			//return "https://s3.amazonaws.com/casadocodigo2/" + multipartFile.getOriginalFilename() + "?noAuth=true";
			
			//http://localhost:9444/s3/casadocodigo2/logo-multitec_pequeno.png
			
			return multipartFile.getOriginalFilename();
			
		} catch (AmazonClientException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Obtém o InputStream do arquivo que está no servidor
	 * @param key String (nome do arquivo)
	 * @return InputStream
	 */
	public InputStream read(String key){
		S3Object object = s3.getObject(bucketName, key);
        InputStream inputStream = object.getObjectContent();
        return inputStream;
	}
	
	/**
	 * Efetua a exclusão/delete do arquivo que está no servidor
	 * @param key String (nome do arquivo)
	 */
	public void deleteObject(String key){
		if(key == null)return;
		s3.deleteObject(bucketName, key);
	}
	
//	private AmazonS3Client client() {
//		AWSCredentials credentials = new BasicAWSCredentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
//		AmazonS3Client newClient = new AmazonS3Client(credentials, new ClientConfiguration());
//		newClient.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
//		newClient.setEndpoint("http://localhost:9444/s3");
//		return newClient;
//	}
	
}
