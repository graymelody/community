package xyz.gray.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import xyz.gray.community.mapper.QuestionMapper;
import xyz.gray.community.model.Question;
import xyz.gray.community.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gray on 2019-08-26 上午 11:05
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("publish")
    public String insertQuestion(Question question, HttpServletRequest request, Model model){
        String title = question.getTitle();
        String description = question.getDescription();
        String tag = question.getTag();

        if (title == null || "".equals(title.trim())) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || "".equals(description.trim())) {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (tag == null || "".equals(tag.trim())) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insertQuestion(question);

        return "index";
    }
}
