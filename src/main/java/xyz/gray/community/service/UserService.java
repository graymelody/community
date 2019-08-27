package xyz.gray.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gray.community.mapper.UserMapper;
import xyz.gray.community.model.User;

/**
 * Created by Gray on 2019-08-27 下午 08:21
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.getByAccountId(user.getAccountId());
        if (dbUser != null) {
            user.setId(dbUser.getId());
            userMapper.update(user);
        }else {
            userMapper.insert(user);
        }
    }
}
