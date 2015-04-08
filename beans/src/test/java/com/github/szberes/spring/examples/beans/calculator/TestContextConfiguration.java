package com.github.szberes.spring.examples.beans.calculator;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Calculator.class)
public class TestContextConfiguration {
}
