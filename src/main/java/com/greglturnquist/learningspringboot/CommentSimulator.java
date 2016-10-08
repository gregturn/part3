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
package com.greglturnquist.learningspringboot;

import com.greglturnquist.learningspringboot.images.ImageRepository;
import com.greglturnquist.learningspringboot.messaging.Comment;
import com.greglturnquist.learningspringboot.messaging.CommentController;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Profile("simulator")
@Component
public class CommentSimulator {

	private final CommentController controller;
	private final ImageRepository repository;

	private final AtomicInteger counter;

	public CommentSimulator(CommentController controller,
							ImageRepository repository) {
		this.controller = controller;
		this.repository = repository;
		this.counter = new AtomicInteger(1);
	}

	@Scheduled(fixedRate = 100)
	public void simulateActivity() {
		repository.findAll().forEach(image -> {
			Comment comment = new Comment();
			comment.setImageId(image.getId());
			comment.setComment(
				"Comment #" + counter.getAndIncrement());
			controller.addComment(comment);
		});
	}
}
// end::tag[]
