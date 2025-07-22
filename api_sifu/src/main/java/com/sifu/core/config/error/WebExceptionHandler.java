package com.sifu.core.config.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = "com.sifu.core.controller.site")
public class WebExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle404(NoHandlerFoundException ex) {
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("path", ex.getRequestURL());
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handle403(AccessDeniedException ex) {
        ModelAndView mav = new ModelAndView("error/403");
        mav.addObject("error", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handle500(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // o logger
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("exception", ex.getMessage());
        mav.addObject("path", request.getRequestURI());
        return mav;
    }
}
