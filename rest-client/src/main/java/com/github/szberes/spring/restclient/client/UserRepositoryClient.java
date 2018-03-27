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

import com.github.szberes.spring.restclient.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryClient {
    private String baseUrl = "http://localhost:8080";

    @Autowired
    private RestTemplate rt;

    public User add(User user) {
        return rt.postForObject(baseUrl + "/users", user, User.class);
    }

    public Optional<User> get(Long id) {
        ResponseEntity<User> entity = rt.exchange(baseUrl + "/users/{id}", HttpMethod.GET, null, User.class, id);
        switch (entity.getStatusCode()) {
            case OK:
                return Optional.ofNullable(entity.getBody());
            case NOT_FOUND:
                return Optional.empty();
            default:
                return Optional.empty(); // TODO
        }

//        return Optional.ofNullable(rt.getForObject(baseUrl + "/users/{id}", User.class, id));
    }

    public List<User> listAll() {
        return Arrays.asList(rt.getForObject(baseUrl + "/users", User[].class));
    }


}
