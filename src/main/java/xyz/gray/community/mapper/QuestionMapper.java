package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    void insertQuestion(Question question);
    @Select("select * from question")
    List<Question> findAll();
}
