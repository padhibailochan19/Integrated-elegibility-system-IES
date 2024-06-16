package com.bailochan.utils;

import java.io.File;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.amazonaws.services.s3.AmazonS3;


@Component
public class S3Utils {
	
	@Value("${bucketName}")
	private String bucketName;

	private final AmazonS3 s3;

	public S3Utils(AmazonS3 s3) {
		this.s3 = s3;
	}

//	public String uploadObject(File f) {
//		
//		s3.putObject(bucketName, f.getName(), f);
//		URL url = s3.getUrl(bucketName, f.getName());
//		return url.toExternalForm();
//	}
	
	public String uploadObject(File file) {
	    String key = generateUniqueKey(file.getName()); // Generate a unique key for the object
	    s3.putObject(bucketName, key, file);
	    URL url = s3.getUrl(bucketName, key);
	    return url.toExternalForm();
	}

	private String generateUniqueKey(String fileName) {
	    // Append a timestamp to the file name to make it unique
	    return fileName + "-" + System.currentTimeMillis();
	}

}
