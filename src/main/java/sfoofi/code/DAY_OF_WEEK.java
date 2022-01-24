package sfoofi.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sfoofi.CommonCode;

@AllArgsConstructor
@Getter
@CommonCode.GroupName("요일")
public enum DAY_OF_WEEK implements CommonCode.Code, CommonCode.CodeExpand {
    MONDAY("월","1"),
    TUESDAY("화","2"),
    WEDNESDAY("수","3"),
    THURSDAY("목","4"),
    FRIDAY("금",""),
    SATURDAY("토",""),
    SUNDAY("일","");

    private String title;
    private String imgUrl;

    @Override
    public CommonCode.CodeDesc getCodeDesc() {
        return CommonCode.CodeDesc.builder()
                .code(this.name())
                .desc(this.title)
                .build();
    }

    @Override
    public CommonCode.CodeDetail getCodeDetail() {
        return CommonCode.CodeDetail.builder()
                .code(this.name())
                .desc(this.title)
                .imgUrl(this.imgUrl)
                .build();
    }
}
