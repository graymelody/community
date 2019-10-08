package xyz.gray.community.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.gray.community.dto.CommentCreateDTO;
import xyz.gray.community.dto.CommentDTO;
import xyz.gray.community.dto.ResultDTO;
import xyz.gray.community.enums.CommentTypeEnum;
import xyz.gray.community.exception.CustomizeErrorCodeImpl;
import xyz.gray.community.model.Comment;
import xyz.gray.community.model.User;
import xyz.gray.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Gray on 2019-08-28 下午 08:55
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("comment")
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        //获取session中的用户信息
        User user = (User)request.getSession().getAttribute("user");
        //判断用户是否登录
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCodeImpl.NO_LOGIN);
        }

        //判断回复的内容
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCodeImpl.CONTENT_IS_EMPTY);
        }

        //创建回复对象，存入数据库
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @GetMapping("comment/{id}")
    @ResponseBody
    public ResultDTO<List<CommentDTO>> subComments(@PathVariable("id") long id) {
        List<CommentDTO> comments = commentService.getByPid(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(comments);
    }
}
