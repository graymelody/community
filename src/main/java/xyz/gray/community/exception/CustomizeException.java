package xyz.gray.community.exception;

/**
 * Created by Gray on 2019-08-28 下午 02:59
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomizeException(CustomizeErrorCodeImpl errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
