package com.yuansong.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

public class ExceptionHandler extends HandlerExceptionResolverComposite {
	
	private final Logger logger = Logger.getLogger(ExceptionHandler.class);
	
	public ModelAndView resolveException(HttpServletRequest request,   
            HttpServletResponse response, Object handler, Exception ex) {
		
		logger.error(ex.getMessage());
		ex.printStackTrace();
				
		Map<String, Object> model = new HashMap<String, Object>();
		
		StringWriter sw = new StringWriter();   
        PrintWriter pw = new PrintWriter(sw, true);   
        ex.printStackTrace(pw);   
        pw.flush();   
        sw.flush();   
			
		model.put("message", ex.getMessage());
		model.put("detail", sw.toString());
		
        return new ModelAndView("errorPage",model);   
    }

}
