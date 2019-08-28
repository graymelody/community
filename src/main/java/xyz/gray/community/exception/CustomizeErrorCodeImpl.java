package xyz.gray.community.exception;

/**
 * Created by Gray on 2019-08-28 下午 03:21
 */
public enum  CustomizeErrorCodeImpl implements CustomizeErrorCode {
    QUESTIPN_NOT_FOUND(1001,"你找的问题不存在了，要不换个试试吧~~~"),
    QUESTION_UPDATE_FAILED(1002,"问题不翼而飞了，更新失败！！！")
    ;

    private Integer code;
    private String message;

    CustomizeErrorCodeImpl(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
