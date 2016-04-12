package com.tamtran.myreceipt.web.exception.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private String errorPage;
    private static final Logger logger = LogManager.getLogger();


    public MyAccessDeniedHandler() {
    }

    public MyAccessDeniedHandler(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ( (line=reader.readLine()) != null ) {
            stringBuilder.append(line).append("\n");
        }

        String retval = stringBuilder.toString();
        logger.info(retval);


        logger.info("<-------------------REQUEST HEADER------------------->");
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            logger.info(headerName + ":" + request.getHeader(headerName));
        }

        logger.info("Arrived in custom access denied handler.");
        HttpSession session = request.getSession();
        logger.info("Session is " +session );
        logger.info("Session id = " + session.getId());
        logger.info("Session max interval="+session.getMaxInactiveInterval());
        logger.info("Session last used="+session.getLastAccessedTime());
        logger.info("Time now="+new Date().getTime());

        logger.info("\n");
        logger.info("csrf:");
        Object csrf = request.getAttribute("_csrf");

        if (csrf==null) {
            logger.info("csrf is null");
        } else {
            logger.info(csrf.toString());
            if (csrf instanceof DefaultCsrfToken) {
                DefaultCsrfToken token = (DefaultCsrfToken) csrf;
                logger.info("Parm name " + token.getParameterName());
                logger.info("Token " + token.getToken());
            }

        }
        logger.info("\n");
        logger.info("Request:");
        logger.info(request.toString());
        logger.info("\n");
        logger.info("Response:");
        logger.info(response.toString());
        logger.info("\n");
        logger.info("Exception:");
        logger.info(accessDeniedException.toString());

        //do some business logic, then redirect to errorPage url
        response.sendRedirect(errorPage);

    }

}
