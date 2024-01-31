package com.anselmojr.desafioanotaai.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;

@Configuration
public class AwsSNSConfig {
	@Value("${aws.region}")
	private String region;
	
	@Value("${aws.accessKeyId}")
	private String accessKeyId;
	
	@Value("${aws.secretKey}")
	private String secretKey;
	
	@Value("${aws.sns.topic.catalog.arn}")
	private String catalogTopArn;
	
	//instance amazon SNS
	@Bean
	public AmazonSNS amazonSNSBuilder() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);
		return AmazonSNSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(region).build();
	}
	
	//
	@Bean(name = "catalogEventsTopic")
	public Topic snsCatalogTopicBuilder() {
		return new Topic().withTopicArn(catalogTopArn);
	}
}
