package com.set;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<String> repayDtSet = new HashSet<String>();  
		
//		repayDtSet.add("201801");
//		repayDtSet.add("201901");
//		repayDtSet.add("202001");
//		repayDtSet.add("201912");
//		repayDtSet.add("201911");
//		repayDtSet.add("201910");
//		System.out.println(Collections.max(repayDtSet));
		
		LocalDate dt = LocalDate.parse("2019-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusMonths(1);
//		dt.plusMonths(1);
		
		System.out.println(dt.format(DateTimeFormatter.ofPattern("yyyy-MM")));
	}

}
