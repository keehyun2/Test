package sfoofi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

@Slf4j
public class CommonCode {

    /**
     * 그룹코드 목록 출력
     * @return
     */
    public static List<GroupCodeDesc> findGroupCode() {
        return Arrays
                .stream(CommonCode.class.getDeclaredClasses()).filter(Class::isEnum).map(vo -> GroupCodeDesc.builder()
                        .groupCode(vo.getSimpleName())
                        .desc(vo.getAnnotation(GroupName.class).value())
                        .codeList(findCode(vo.getSimpleName()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 그룹코드 내 하위 코드출력
     * @param groupCode
     * @return
     * @throws ClassNotFoundException
     */
    public static List<CodeDesc> findCode(String groupCode)  {
        Class<?> clazz1 = CommonCode.class;
        try {
            clazz1 = Class.forName("sfoofi.code." + groupCode);
        } catch (ClassNotFoundException e) {
             log.error("코드조회실패", e);
        }
        return Arrays.stream(clazz1.getEnumConstants()).map(vo -> ((Code) vo).getCodeDesc())
                .collect(Collectors.toList());
    }

    /**
     * 그룹코드 내 하위 코드의 상세 내역 출력
     * @param groupCode
     * @return
     * @throws ClassNotFoundException
     */
    public static List<CodeDetail> findCodeDetail(String groupCode)  {
        Class<?> clazz1 = CommonCode.class;
        try {
            clazz1 = Class.forName("sfoofi.code." + groupCode);
        } catch (ClassNotFoundException e) {
            log.error("코드조회실패", e);
        }
        return Arrays.stream(clazz1.getEnumConstants()).map(vo -> ((CodeExpand) vo).getCodeDetail())
                .collect(Collectors.toList());
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GroupCodeDesc {
        private String groupCode;
        private String desc;
        private List<CodeDesc> codeList;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CodeDesc {
        private String code;
        private String desc;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    public static class CodeDetail extends CodeDesc {
        private String imgUrl;
    }

    public interface Code {
        CodeDesc getCodeDesc();
    }

    public interface CodeExpand {
        CodeDetail getCodeDetail();
    }

    /**
     * GroupName 어노테이션 선언
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GroupName {
        String value();
    }

}
