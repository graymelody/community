package xyz.gray.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gray.community.mapper.UserMapper;
import xyz.gray.community.model.User;
import xyz.gray.community.model.UserExample;

import java.util.List;

/**
 * Created by Gray on 2019-08-27 下午 08:21
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        if (users != null) {
            user.setId(users.get(0).getId());
            user.setGmtCreate(null);
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            userMapper.insertSelective(user);
        }
    }
}
