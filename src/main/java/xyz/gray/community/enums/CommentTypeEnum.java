package xyz.gray.community.enums;

/**
 * Created by Gray on 2019-10-05 下午 12:32
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2),
    ;
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
