package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.Update;
import xyz.gray.community.model.Question;

/**
 * Created by Gray on 2019-08-28 下午 06:36
 */
public interface QuestionExtMapper {

    @Update("update question set view_count = view_count + #{viewCount} where id = #{id}")
    int incView(Question question);
}
