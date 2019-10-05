package xyz.gray.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gray.community.dto.PaginationDTO;
import xyz.gray.community.dto.QuestionDTO;
import xyz.gray.community.exception.CustomizeErrorCodeImpl;
import xyz.gray.community.exception.CustomizeException;
import xyz.gray.community.mapper.QuestionExtMapper;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 首页展示列表
     * @param page 当前页
     * @param size 每页显示的条目数
     * @return 显示的列表数据
     */
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

    /**
     * 我的提问展示列表
     * @param id 账户的id
     * @param page 当前页
     * @param size 每页显示的条目数
     * @return 列表显示的数据
     */
    public PaginationDTO<QuestionDTO> list(Long id, int page, int size) {
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

    /**
     * 提问详情页面
     * @param id 提问的id
     * @return 这条提问的数据
     */
    public QuestionDTO getById(long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCodeImpl.QUESTION_NOT_FOUND);
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
                throw new CustomizeException(CustomizeErrorCodeImpl.INVALID_OPERATION);
            }
        } else {
            questionMapper.insertSelective(question);
        }
    }

    /**
     * 修改提问的阅读数
     * @param id 这条提问的id
     */
    public void incView(long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
