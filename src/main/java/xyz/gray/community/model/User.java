package xyz.gray.community.model;

import lombok.Data;

/**
 * Created by Gray on 2019-08-25 下午 09:22
 */
@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private String bio;
    private String email;
    private String login;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
