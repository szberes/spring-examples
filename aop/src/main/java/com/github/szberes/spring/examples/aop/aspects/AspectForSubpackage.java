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

package com.github.szberes.spring.examples.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectForSubpackage {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectForSubpackage.class);

	@Pointcut("within(com.github.szberes.spring.examples.aop.advisedClasses.subpackage..*)")
	public void inSubpackage() {}

	@Pointcut("execution(public * *(..))")
	private void anyPublicMethod() {}

	@Before("inSubpackage() && anyPublicMethod()")
	public void logBefore(JoinPoint joinPoint) {
		LOGGER.info("joinPoint: " + joinPoint);
	}

}
