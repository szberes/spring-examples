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

package com.github.szberes.spring.examples.security.java;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class JavaApiSecurityTest {

	@Autowired
	private TestedService testedService;

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void getMessageUnauthenticatedTest() {
		testedService.getMessage();
	}

	@Test
	@WithMockUser(username = "Bela", password = "password")
	public void getMessageAuthenticatedTest() {
		assertEquals("Hello Bela", testedService.getMessage());
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void testPostMessageUnauthenticated() {
		testedService.postMessage("message");
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "Bela", password = "password")
	public void testPostMessageAsUserShouldThrow() {
		testedService.postMessage("message");
	}

	@Test
	@WithMockUser(username = "Bela", password = "password", roles = {"ADMIN"})
	public void testPostMessageAsAdminShouldSucceed() {
		testedService.postMessage("message");
	}

	@Test
	@WithMockUser(username = "Bela", password = "password", roles = {"ADMIN", "USER"})
	public void testAdminReadMessages() {
		assertEquals("Hello Bela", testedService.getMessage());
	}

	@Test
	@WithMockUser(username = "Bela", password = "password", roles = {"ADMIN", "USER"})
	public void testMockUserProgrammatically() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		User user = new User("Bela", "password", authorities);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		assertEquals("Hello Bela", testedService.getMessage());
	}
}
