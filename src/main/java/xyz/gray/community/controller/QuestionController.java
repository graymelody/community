package xyz.gray.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.gray.community.dto.QuestionDTO;
import xyz.gray.community.service.QuestionService;

/**
 * Created by Gray on 2019-08-27 下午 06:52
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("question/{id}")
    public String question(@PathVariable("id") int id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}