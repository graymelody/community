package xyz.gray.community.dto;

import lombok.Data;

/**
 * Created by Gray on 2019-08-25 下午 07:00
 */
@Data
public class GithubUser {
    private String login;
    private long id;
    private String name;
    private String email;
    private String bio;
    private String avatarUrl;
}
