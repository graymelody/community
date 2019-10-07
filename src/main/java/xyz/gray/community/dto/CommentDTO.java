package xyz.gray.community.dto;

import lombok.Data;
import xyz.gray.community.model.User;

/**
 * Created by Gray on 2019-10-07 上午 08:35
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    private User CreateUser;
}
