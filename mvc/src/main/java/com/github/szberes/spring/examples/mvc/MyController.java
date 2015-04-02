/*
 * Copyright 2015 Szabolcs Balazs Beres.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.szberes.spring.examples.mvc;

import com.github.szberes.spring.examples.mvc.domain.Message;
import com.github.szberes.spring.examples.mvc.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.Date;

@RequestMapping(value = "/")
@Controller
public class MyController {

	public static class MessageRestDto {
		private String username;
		private String message;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@Autowired
	private MessageRepository messageRepository;

	@RequestMapping(value = "messages", method = RequestMethod.GET)
	public String messages(Model model) {
		model.addAttribute(messageRepository.getMessages());
		return "messageBoard";
	}


	@RequestMapping(value = "messages", method = RequestMethod.POST)
	public String newMessage(MessageRestDto body) {
		messageRepository.addMessage(new Message(body.getMessage(), new Date(), body.getUsername()));
		return "redirect:/messages/";
	}

	@PostConstruct
	public void addSomeMessage() {
		messageRepository.addMessage(new Message("text0", new Date(), "someUser"));
		messageRepository.addMessage(new Message("text1", new Date(), "someUser"));
		messageRepository.addMessage(new Message("text2", new Date(), "someUser"));
		messageRepository.addMessage(new Message("text3", new Date(), "someUser"));
	}
}
