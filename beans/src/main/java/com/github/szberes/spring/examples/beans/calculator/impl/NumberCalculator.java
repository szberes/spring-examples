package com.github.szberes.spring.examples.beans.calculator.impl;

import com.github.szberes.spring.examples.beans.calculator.Calculator;
import org.springframework.stereotype.Component;

@Component
public class NumberCalculator implements Calculator {
	@Override
	public int add(int a, int b) {
		return a + b;
	}
}
