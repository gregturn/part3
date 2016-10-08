package com.greglturnquist.learningspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

// tag::code[]
@SpringBootApplication
@EnableAutoConfiguration//(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableBinding(NewComments.class)
public class LearningSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBootApplication.class, args);
	}
}
// end::code[]