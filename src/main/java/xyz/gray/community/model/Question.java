package xyz.gray.community.model;

import lombok.Data;

/**
 * Created by Gray on 2019-08-26 下午 01:57
 */
@Data
public class Question {
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
}
