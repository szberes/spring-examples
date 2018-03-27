/*
 * Copyright 2018 Szabolcs Balazs Beres.
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

package com.github.szberes.spring.restclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.szberes.spring.restclient.common.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryClientTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepositoryClient client;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws Exception {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAdd() throws Exception { // TODO content
        mockServer.expect(requestTo("http://localhost:8080/users")).andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .body(toJson(new User(2L,"Bela", "", "")))
                        .contentType(MediaType.APPLICATION_JSON));

        User actual = client.add(new User("Bela", "Kovacs", "+3615551235"));

        assertNotNull(actual.getId());
        assertEquals( "Bela", actual.getFirstName());
        mockServer.verify();
    }

    private String toJson(User user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void get() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/users/1")).andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .body(toJson(new User(1L,"Bela", "", "")))
                        .contentType(MediaType.APPLICATION_JSON));

        Optional<User> actual = client.get(1L);

        assertTrue(actual.isPresent());
        assertEquals( "Bela", actual.get().getFirstName());
        mockServer.verify();
    }

    @Test
    public void get404() throws Exception {
        mockServer.expect(requestTo("http://localhost:8080/users/1")).andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        assertFalse(client.get(1L).isPresent());
        mockServer.verify();
    }

    @Test
    public void listAll() throws Exception {
    }

}