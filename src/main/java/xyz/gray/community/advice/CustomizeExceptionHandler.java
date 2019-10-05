package xyz.gray.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import xyz.gray.community.dto.ResultDTO;
import xyz.gray.community.exception.CustomizeErrorCodeImpl;
import xyz.gray.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Gray on 2019-08-28 下午 02:46
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (exception instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) exception);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCodeImpl.SYS_ERROR);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {

            }
            return null;
        } else {
            ModelAndView mv = new ModelAndView();
            if (exception instanceof CustomizeException) {
                mv.addObject("message", exception.getMessage());
            } else {
                mv.addObject("message", CustomizeErrorCodeImpl.SYS_ERROR.getMessage());
            }
            mv.setViewName("error");
            return mv;
        }
    }
}
