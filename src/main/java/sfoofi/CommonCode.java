package sfoofi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CommonCode {
    @Getter
    @Builder
    @AllArgsConstructor
    private static class CodeDesc {
        private String code;
        private String desc;

        @Override
        public String toString() {
            return this.code;
        }
    }

    public interface Code {
        abstract CodeDesc getCodeDesc();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @interface GroupName {
        String value() default "";
    }

    @AllArgsConstructor
    @Getter
    @GroupName("zxczxc")
    public enum STORE_TYPE implements Code {
        ST01("가게형태01"), ST02("가게형태02"), ST03("가게형태03"), ST04("가게형태04");

        private String title;

        @Override
        public CodeDesc getCodeDesc() {
            return CodeDesc.builder().code(this.name()).desc(this.title).build();
        }
    }
}