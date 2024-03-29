package sfoofi;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import sfoofi.CommonCode.Code;
import sfoofi.CommonCode.GroupName;

public class TestCommonCode {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

//        String a = Arrays.stream(CommonCode.class.getDeclaredClasses()).filter(Class::isEnum).map(Class::getSimpleName)
//                .collect(Collectors.joining(","));
//        System.out.println(a);
//
//        System.out.println(CommonCode.class.getDeclaredClasses()[3].getAnnotation(GroupName.class).value());
//
//        Class<?> clazz1 = Class.forName("sfoofi.CommonCode$" + "STORE_TYPE");
//        for (Object obj : clazz1.getEnumConstants()) {
//            System.out.println(((Code) obj).getCodeDesc());
//        }

        String jsonInString = mapper.writeValueAsString(CommonCode.findCode("DAY_OF_WEEK"));

        System.out.println(jsonInString);

        String jsonInString2 = mapper.writeValueAsString(CommonCode.findCodeDetail("DAY_OF_WEEK2"));

        System.out.println(jsonInString2);
    }
}