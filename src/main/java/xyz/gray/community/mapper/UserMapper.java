package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{id}")
    User findById(@Param("id") String id);
}
