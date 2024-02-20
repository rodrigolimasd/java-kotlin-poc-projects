package com.finexti.pocdynamodb;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class PocDynamodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocDynamodbApplication.class, args);
	}


	@Bean
	@Primary
	public DynamoDBMapperConfig dynamoDBMapperConfig() {
		return DynamoDBMapperConfig.DEFAULT;
	}

	@Bean
	@Primary
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB,
										 DynamoDBMapperConfig config) {
		return new DynamoDBMapper(amazonDynamoDB, config);
	}
	@Bean
	@Primary
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_EAST_1).build();
	}

}
