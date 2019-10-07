package xyz.gray.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.gray.community.dto.CommentDTO;
import xyz.gray.community.enums.CommentTypeEnum;
import xyz.gray.community.exception.CustomizeErrorCodeImpl;
import xyz.gray.community.exception.CustomizeException;
import xyz.gray.community.mapper.*;
import xyz.gray.community.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Gray on 2019-10-05 下午 12:20
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {

        //判断是否选中问题进行回复
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCodeImpl.TARGET_PARAM_NOT_FOUND);
        }

        //判断回复的类型是否合法
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCodeImpl.TYPE_PARAM_WRONG);
        }

        //判断回复的类型
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //回复评论
            //用当前回复的父ID查询出当前回复哪条评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.COMMENT_NOT_FOUND);
            }

            //再根据当前评论的回复去查询提问
            Question question = questionMapper.selectByPrimaryKey(parentComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
            }

            //确保提问和评论都存在的情况下插入当前的回复
            commentMapper.insertSelective(comment);

            //修改评论回复数
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
        } else {
            //回复问题
            //查询出回复的是哪个问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
            }

            //确保提问存在的情况下插入当前的回复
            comment.setCommentCount(0);
            commentMapper.insertSelective(comment);

            //修改问题的回复数
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }

    }

    public List<CommentDTO> getByPid(long id, CommentTypeEnum commentTypeEnum) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(commentTypeEnum.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);

        if (comments == null || comments.isEmpty() ) {
            return new ArrayList<>();
        }

        //把所有评论创建者的ID提取出来 去重
        List<Long> commentators = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());

        //根据所有品论者的ID查找出用户信息
        UserExample userExaple = new UserExample();
        userExaple.createCriteria().andIdIn(commentators);
        List<User> users = userMapper.selectByExample(userExaple);

        //把用户列表转换成Map方便后续遍历
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        //将Comment转成CommentDTO返回
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setCreateUser(userMap.get(commentDTO.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;
    }
}
