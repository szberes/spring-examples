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

package com.github.szberes.spring.examples.aop.advised.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyClass {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyClass.class);

	public void doNothing() {
		LOGGER.info("doNothing()");
	}

	public void throwException() {
		LOGGER.info("throwException()");
		throw new RuntimeException("Exception message");
	}

	public int add(int a, int b) {
		LOGGER.info("add(" + a + "," + b +")");
		return a + b;
	}
}
