package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.gray.community.model.Question;

import java.util.List;

/**
 * Created by Gray on 2019-08-26 下午 02:04
 */
@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,creator,tag,gmt_create,gmt_modified) values(#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void insert(Question question);

    @Select("select * from question limit #{page}, #{size}")
    List<Question> list(@Param("page") int page, @Param("size") int size);

    @Select("select count(1) from question")
    int count();

    @Select("select * from question where creator = #{id} limit #{page}, #{size}")
    List<Question> listByCreator(@Param("id") Integer id, @Param("page") int page, @Param("size") int size);

    @Select("select count(1) from question where creator = #{id}")
    int countByCreator(@Param("id") Integer id);

    @Select("select * from question where id = #{id}")
    Question getById(int id);

    @Update("update question set " +
            "title = #{title}," +
            "description = #{description}," +
            "gmt_modified = #{gmtModified}," +
            "tag = #{tag} " +
            "where id = #{id}")
    void update(Question question);
}
