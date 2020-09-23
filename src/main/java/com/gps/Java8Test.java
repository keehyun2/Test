package com.gps;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Test {
	public static void main(String[] args) {
//		Optional<String> stringOptional = Optional.ofNullable("hi");
//		Optional<Integer> integerOptional = stringOptional.map(s -> s.length());
//		Optional<Integer> integerOptional2 = stringOptional.map(new Function<String, Integer>() {
//		            @Override
//		            public Integer apply(String s) {
//		                return s.length();
//		            }
//		        });
//		Optional<String> stringOptional2 = stringOptional.map(s -> " java");
		
//		String[][] data = new String[][]{ {"1", "2"}, {"3", "4"} };
//
//		Stream<Stream<String>> map = Arrays.stream(data).map(x -> Arrays.stream(x));
//		map.forEach(s -> s.forEach(System.out::println));
//		System.out.println("=========================");
//
//		Stream<String> flatmap = Arrays.stream(data).flatMap(x -> Arrays.stream(x));
//		flatmap.forEach(System.out::println);
		
//		String[][] namesArray = new String[][]{
//	        {"kim", "taeng"}, {"mad", "play"},
//	        {"kim", "mad"}, {"taeng", "play"}};
//
//        Set<String> namesWithFlatMap = Arrays.stream(namesArray)
//	        .flatMap(innerArray -> Arrays.stream(innerArray))
//	        .filter(name -> name.length() > 3)
//	        .collect(Collectors.toSet());
//        
//        namesWithFlatMap.forEach(System.out::println);
//        
//        // 2차원 배열 선언 생략
//        Set<String> namesWithMap = Arrays.stream(namesArray)
//                .map(innerArray -> Arrays.stream(innerArray)
//                        .filter(name -> name.length() > 3)
//                        .collect(Collectors.toSet()))
//                .collect(HashSet::new, Set::addAll, Set::addAll);
		
		//java 8 Lambda
//		final List<Object> names = Arrays.asList((Object)"Sehoon", "Songwoo", "Chan", "Youngsuk", "Dajung");
//
//		List<Object> LongerEliment2 = names.stream()
//		        .reduce((Object)"", (total, name2) -> {
//		        	return total;
//		        });
//		System.out.println("java 8 " + LongerEliment2);
		
		String arr[][] = {
			    {"minus one", "zero", "one"}, 
			    {"two", "Three"}, 
			    {"Four", "Five", "Six"}, 
			    {"eight", "ten"}
			};

			Stream.of(arr)
			        .flatMap(Stream::of)
			        .forEach(System.out::println);

//			int arr[][] = {{1, 2, 3}, {4, 8}, {9, 10, 20}, {11, 22}};
//			Stream.of(arr)
//			        .flatMapToInt(IntStream::of)
//			        .forEach(System.out::println);

	}
}
