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
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Controller
public class CommentController {

	private final NewComments newComments;

	private final CounterService counterService;

	public CommentController(NewComments newComments, CounterService counterService) {
		this.newComments = newComments;
		this.counterService = counterService;
	}

	@PostMapping("/comments")
	public String addComment(Comment newComment) {
		newComments.output().send(MessageBuilder.withPayload(newComment).build());
		counterService.increment("comments.total.produced");
		counterService.increment(
			"comments." + newComment.getImageId() + ".produced");
		return "redirect:/";
	}

}
// end::code[]