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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableAsync
public class AsyncExampleMain implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExampleMain.class);

	@Autowired
	private AsyncExecutableClass asyncExecutableClass;

	public static void main(String[] args) {
		SpringApplication.run(AsyncExampleMain.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<Future<Void>> tasks = new ArrayList<>(10);
		IntStream.range(0,10).forEach(i -> tasks.add(asyncExecutableClass.waitFor(1000)));

		for (Future<Void> task : tasks) {
			task.get();
		}

		stopWatch.stop();
		LOGGER.info(stopWatch.prettyPrint());
	}

}
