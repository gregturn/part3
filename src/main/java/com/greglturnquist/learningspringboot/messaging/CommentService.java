/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.learningspringboot.messaging;

import com.greglturnquist.learningspringboot.NewComments;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author Greg Turnquist
 */
@Service
public class CommentService {

	private final CommentWriterRepository repository;

	private final CounterService counterService;

	public CommentService(CommentWriterRepository repository,
						  CounterService counterService) {
		this.repository = repository;
		this.counterService = counterService;
	}

	// tag::code[]
	@StreamListener(NewComments.CHANNEL_NAME)
	public void save(Comment newComment) {
		System.out.println("Received " + newComment.toString());
		repository.save(newComment);
		counterService.increment("comments.total.consumed");
		counterService.increment(
			"comments." + newComment.getImageId() + ".consumed");
	}
	// end::code[]

	@Bean
	Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	CommandLineRunner setUp(CommentWriterRepository repository) {
		return args -> {
			repository.deleteAll();
		};
	}

}
