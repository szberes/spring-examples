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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Date;

@RequestMapping(value = "/")
@Controller
public class MyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);
	@Autowired
	private MessageRepository messageRepository;

	@RequestMapping(value = "messages", method = RequestMethod.GET)
	public String messages(Model model) {
		model.addAttribute(messageRepository.getMessages());
		model.addAttribute("newMessage", new MessageRestDto());
		return "messageBoard";
	}

	@RequestMapping(value = "messages", method = RequestMethod.POST)
	public String newMessage(@Valid MessageRestDto newMessage, Errors errors) {
		if (errors.hasErrors()) {
			LOGGER.info(errors.getAllErrors().toString());
			return "redirect:/messages/";
		}
		messageRepository.addMessage(new Message(newMessage.getMessage(), new Date(), getUserName()));
		return "redirect:/messages/";
	}

	@RequestMapping(value = "myProfile", method = RequestMethod.GET)
	public String myMessages(Model model) {


		String userName = getUserName();

		model.addAttribute("userName", userName);
		return "userProfile";
	}

	private String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = "anonymous";
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		}
		return userName;
	}
	@Autowired
	SecurityContextRepository repository;

	@Autowired
	AuthenticationManager authenticationManager;

	private void loginUser(String username, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//		User details = new User(username);
//		token.setDetails(details);

		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
//			return new LoginStatus(auth.isAuthenticated(), auth.getName());
		} catch (BadCredentialsException e) {
//			return new LoginStatus(false, null);
		}
	}

	@PostConstruct
	public void addSomeMessage() {
		messageRepository.addMessage(new Message("text0", new Date(), "someUser"));
		messageRepository.addMessage(new Message("text1", new Date(), "someUser"));
		messageRepository.addMessage(new Message("text2", new Date(), "someUser"));
		messageRepository.addMessage(new Message("text3", new Date(), "someUser"));
	}
}
