package com.gps;

public class Test2 {

	public static void main(String[] args) {
		
		System.out.println("2020-12-31".subSequence(8, 9).toString());
		
		System.out.println(String.format("%08d", Integer.parseInt("123")));
		System.out.println(String.format("%08d", Integer.parseInt("12")));
	}

}
