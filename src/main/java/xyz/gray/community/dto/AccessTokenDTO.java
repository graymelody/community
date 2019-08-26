package xyz.gray.community.dto;

import lombok.Data;

/**
 * Created by Gray on 2019-08-25 下午 06:24
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
