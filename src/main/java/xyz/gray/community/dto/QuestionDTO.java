package xyz.gray.community.dto;

import lombok.Data;
import xyz.gray.community.model.User;

/**
 * Created by Gray on 2019-08-26 下午 06:24
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer creator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
