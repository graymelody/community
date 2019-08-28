package xyz.gray.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gray.community.dto.PaginationDTO;
import xyz.gray.community.dto.QuestionDTO;
import xyz.gray.community.exception.CustomizeErrorCodeImpl;
import xyz.gray.community.exception.CustomizeException;
import xyz.gray.community.mapper.QuestionMapper;
import xyz.gray.community.mapper.UserMapper;
import xyz.gray.community.model.Question;
import xyz.gray.community.model.QuestionExample;
import xyz.gray.community.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gray on 2019-08-26 下午 06:27
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO<QuestionDTO> list(int page, int size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        int count = (int) questionMapper.countByExample(new QuestionExample());
        int totalPage = count % size == 0 ? count / size : count / size + 1;
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        int start = (page - 1) * size;

        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(start, size));

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }

    public PaginationDTO<QuestionDTO> list(Integer id, int page, int size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(id);
        int count = (int) questionMapper.countByExample(example);
        int totalPage = count % size == 0 ? count / size : count / size + 1;
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        int start = (page - 1) * size;

        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(start, size));

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }

    public QuestionDTO getById(int id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCodeImpl.QUESTIPN_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() != null) {
            question.setGmtCreate(null);
            int result = questionMapper.updateByPrimaryKeySelective(question);
            if (result < 1) {
                throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_UPDATE_FAILED);
            }
        } else {
            questionMapper.insertSelective(question);
        }
    }
}
