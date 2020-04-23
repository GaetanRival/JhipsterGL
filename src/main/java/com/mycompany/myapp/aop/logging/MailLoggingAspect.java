package com.mycompany.myapp.aop.logging;

import java.util.logging.Logger;

import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.dto.UserDTO;





@Aspect
public class MailLoggingAspect {
	private Logger mailLogger = (Logger) LogFactory.getLog(MailService.class);

    @Pointcut("execution(* com.mycompany.myapp.service.MailService.send*(..))")
    public void sendEmail() {}
    
    @AfterReturning("sendEmail()")
    public void sendNewEmail(JoinPoint joinPoint){
        
        Object[] lArgs = joinPoint.getArgs();
        UserDTO user = (UserDTO) lArgs[0];
        
        ThreadContext.put("username", user.getFirstName());
        ThreadContext.put("field", "ALL");
        ThreadContext.put("from_value", "");
        ThreadContext.put("to_value", user.toString());
        Logger userLogger = null;
		userLogger.info("New user");
        ThreadContext.clearAll();
    }
}
