package com.java8;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class 자바8_Optional_test {

	@Test
	public void test() {
		assertEquals(Optional.of(Optional.of("STRING")), Optional.of("STRING").map(Optional::of));

		assertEquals(Optional.of("STRING"), Optional.of("STRING").flatMap(Optional::of));
	}

}
