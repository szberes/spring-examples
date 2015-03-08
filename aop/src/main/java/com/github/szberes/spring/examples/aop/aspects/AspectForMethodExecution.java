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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectForMethodExecution {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectForMethodExecution.class);

	@Pointcut("execution(* com.github.szberes.spring.examples.aop.advised.classes.MyClass.add(..)))")
	public void addPointCut() {}

	@Before(value = "addPointCut() && args(a,b))",
			argNames = "joinPoint,a,b")
	public void logBefore(JoinPoint joinPoint, int a, int b) {
		LOGGER.info(joinPoint.toString() + " Params: " + a + ", " + b);
	}

	@AfterReturning(value = "addPointCut()))", returning = "retVal")
	public void logAfter(JoinPoint joinPoint, int retVal) {
		LOGGER.info(joinPoint.toString() + " finished: " + retVal);
	}

	@Around("execution(* com.github.szberes.spring.examples.aop.advised.classes.MyClass.doNothing())")
	public void aroundDoNothing(ProceedingJoinPoint joinPoint) {
		LOGGER.info("Around: " + joinPoint);
		try {
			joinPoint.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		LOGGER.info("Around advice done: " + joinPoint);
	}

	@AfterThrowing(value = "execution(* com.github.szberes.spring.examples.aop.advised.classes.MyClass.throwException())",
					throwing = "ex")
	public void afterThrowing(RuntimeException ex) {
		LOGGER.info("AfterThrowing exception: " + ex);
	}
}
