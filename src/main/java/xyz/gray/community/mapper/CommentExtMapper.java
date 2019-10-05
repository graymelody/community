package xyz.gray.community.mapper;

import org.apache.ibatis.annotations.Update;
import xyz.gray.community.model.Comment;

/**
 * Created by Gray on 2019-10-05 下午 01:01
 */
public interface CommentExtMapper {
    @Update("update comment set comment_count = comment_count + #{commentCount} where id = #{id}")
    int incCommentCount(Comment comment);
}
