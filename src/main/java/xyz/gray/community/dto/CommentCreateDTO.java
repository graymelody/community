package xyz.gray.community.dto;

import lombok.Data;

/**
 * Created by Gray on 2019-08-28 下午 08:52
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
