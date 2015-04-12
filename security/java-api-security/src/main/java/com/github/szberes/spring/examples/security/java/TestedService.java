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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@PreAuthorize("isAuthenticated() and hasRole('ROLE_USER')")
public class TestedService {

	public String getMessage() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return "Hello " + ((UserDetails) authentication.getPrincipal()).getUsername();
	}

	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	public void postMessage(String message) {
	}
}
