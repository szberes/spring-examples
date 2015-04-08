package com.github.szberes.spring.examples.beans.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfiguration.class)
public class CalculatorTest {

	@Autowired
	private Calculator numberCalculator;

	@Autowired
	private Calculator stringCalculator;

	@Autowired
	@Qualifier("numberAsStringCalculator")
	private Calculator numberAsStringCalculator;

	@Test
	public void testNumberCalculator() throws Exception {
		assertEquals(3, numberCalculator.add(1,2));
	}

	@Test
	public void testStringCalculator() throws Exception {
		assertEquals(12, stringCalculator.add(1, 2));
	}

	@Test
	public void testNumberAsStringCalculator() throws Exception {
		assertEquals(12, numberAsStringCalculator.add(1, 2));
	}

}
