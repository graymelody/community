package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.gray.community.model.User;

/**
 * Created by Gray on 2019-08-25 下午 09:29
 */
@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatarUrl,bio,email,login) " +
            " values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio},#{email},#{login})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User getByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User getById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User getByAccountId(String accountId);

    @Update("update user " +
            "set " +
            "name = #{name}," +
            "token = #{token}," +
            "gmt_modified = #{gmtModified}," +
            "avatarUrl = #{avatarUrl}," +
            "bio = #{bio}," +
            "email = #{email}," +
            "login = #{login} " +
            "where account_id = #{accountId} " +
            "and id = #{id}")
    void update(User user);
}
