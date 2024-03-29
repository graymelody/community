package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.gray.community.model.Question;

import java.util.List;

/**
 * Created by Gray on 2019-08-28 下午 06:36
 */
public interface QuestionExtMapper {

    @Update("update question set view_count = view_count + #{viewCount} where id = #{id}")
    int incView(Question question);

    @Update("update question set comment_count = comment_count + #{commentCount} where id = #{id}")
    int incCommentCount(Question question);

    @Select("select id, title, tag from question where tag regexp #{tag} and id != #{id}")
    List<Question> selByTag(Question question);
}
