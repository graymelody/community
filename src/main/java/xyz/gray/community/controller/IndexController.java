package xyz.gray.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.gray.community.dto.PaginationDTO;
import xyz.gray.community.dto.QuestionDTO;
import xyz.gray.community.mapper.UserMapper;
import xyz.gray.community.model.User;
import xyz.gray.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Gray on 2019-08-25 下午 02:50
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "5") int size) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }

        PaginationDTO<QuestionDTO> pagination = questionService.list(page, size);
        model.addAttribute("pagination",pagination);

        return "index";
    }
}
