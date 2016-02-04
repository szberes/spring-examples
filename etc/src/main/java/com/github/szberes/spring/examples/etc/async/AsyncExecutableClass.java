/*
 * Copyright 2016 Szabolcs Balazs Beres.
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

package com.github.szberes.spring.examples.etc.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Async
@Component
public class AsyncExecutableClass {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutableClass.class);

	public Future<Void> waitFor(int milliSecs) {
		LOGGER.info(getClass().getSimpleName() + ".waitFor() started in thread: " + Thread.currentThread().getId());
		try {
			Thread.sleep(milliSecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info(getClass().getSimpleName() + ".waitFor() finished in thread: " + Thread.currentThread().getId());
		return new AsyncResult<>(null);
	}

}
