package xyz.gray.community.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xyz.gray.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gray on 2019-08-28 下午 02:46
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable exception) {
        ModelAndView mv = new ModelAndView();
        if (exception instanceof CustomizeException) {
            mv.addObject("message", exception.getMessage());
        } else {
            mv.addObject("message", "请稍后再试...（づ￣3￣）づ╭❤～");
        }
        mv.setViewName("error");
        return mv;
    }
}
