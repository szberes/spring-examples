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

package com.github.szberes.spring.examples.mvc.repository;

import com.github.szberes.spring.examples.mvc.domain.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryMessageRepository implements MessageRepository {

	private Map<Long, Message> data = new ConcurrentHashMap<Long, Message>();

	private AtomicLong nextId = new AtomicLong();

	@Override
	public List<Message> getMessages() {
		return new ArrayList<>(data.values());
	}

	@Override
	public void addMessage(Message message) {
		message.setId(nextId.getAndIncrement());
		data.put(message.getId(), message);
	}
}
