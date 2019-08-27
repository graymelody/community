package xyz.gray.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gray.community.dto.PaginationDTO;
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

    public PaginationDTO<QuestionDTO> list(int page, int size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        int count = questionMapper.count();
        int totalPage = count % size == 0 ? count / size : count / size + 1;
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        int start = (page - 1) * size;

        List<Question> questions = questionMapper.list(start, size);

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }

    public PaginationDTO<QuestionDTO> list(String id, int page, int size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        int count = questionMapper.countByCreator(id);
        int totalPage = count % size == 0 ? count / size : count / size + 1;
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        int start = (page - 1) * size;

        List<Question> questions = questionMapper.listByCreator(id, start, size);

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }
}
