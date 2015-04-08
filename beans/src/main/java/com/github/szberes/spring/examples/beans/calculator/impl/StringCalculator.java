package com.github.szberes.spring.examples.beans.calculator.impl;

import com.github.szberes.spring.examples.beans.calculator.Calculator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("numberAsStringCalculator")
public class StringCalculator implements Calculator {
	@Override
	public int add(int a, int b) {
		return Integer.parseInt("" + a + b);
	}
}
