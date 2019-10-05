package xyz.gray.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.gray.community.dto.CommentDTO;
import xyz.gray.community.model.Comment;

/**
 * Created by Gray on 2019-08-28 下午 08:55
 */
@Controller
public class CommentController {

    @PostMapping("comment")
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setType(commentDTO.getType());
        comment.setParentId(commentDTO.getParentId());
        comment.setLikeCount(0L);
        comment.setCommentator(1L);
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        return comment;
    }
}
