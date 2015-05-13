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

package com.github.szberes.spring.examples.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		createInMemoryAuthentication(auth);
	}

	private void createInMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER").and()
				.withUser("admin").password("password").roles("USER", "ADMIN");
	}

	private void createLdapAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.ldapAuthentication()
				.userSearchFilter("(uid={0})")
				.groupSearchFilter("member={0}");
	}

	private void createLdapAuthentication2(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.ldapAuthentication()
				.userSearchBase("ou=people")
				.userSearchFilter("(uid={0})")
				.groupSearchBase("ou=groups")
				.groupSearchFilter("member={0}")
//		.passwordCompare()
//				.passwordEncoder(new Md5PasswordEncoder())
//				.passwordAttribute("passcode");
		;
	}

	private void createLdapAuthenticationWithRemoteServer(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.ldapAuthentication()
				.userSearchBase("ou=people")
				.userSearchFilter("(uid={0})")
				.groupSearchBase("ou=groups")
				.groupSearchFilter("member={0}")
				.contextSource()
				.url("ldap://habuma.com:389/dc=habuma,dc=com");
	}

	private void createLdapAuthenticationWithEmbeddedServer(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.ldapAuthentication()
				.userSearchBase("ou=people")
				.userSearchFilter("(uid={0})")
				.groupSearchBase("ou=groups")
				.groupSearchFilter("member={0}")
				.contextSource()
				.root("dc=habuma,dc=com");
	}

	private void createLdapAuthenticationWithEmbeddedServerUsersInLdif(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.ldapAuthentication()
				.userSearchBase("ou=people")
				.userSearchFilter("(uid={0})")
				.groupSearchBase("ou=groups")
				.groupSearchFilter("member={0}")
				.contextSource()
				.root("dc=habuma,dc=com")
				.ldif("classpath:users.ldif");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().authenticated()
		http.authorizeRequests().anyRequest().permitAll()
//				.antMatchers(HttpMethod.POST, "/messages").authenticated()
				.and()
				.formLogin().and()
				.httpBasic();
	}
}
