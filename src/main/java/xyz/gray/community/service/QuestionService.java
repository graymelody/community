package xyz.gray.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gray.community.dto.QuestionDTO;
import xyz.gray.community.mapper.QuestionMapper;
import xyz.gray.community.mapper.UserMapper;
import xyz.gray.community.model.Question;
import xyz.gray.community.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gray on 2019-08-26 下午 06:27
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questions = questionMapper.findAll();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
