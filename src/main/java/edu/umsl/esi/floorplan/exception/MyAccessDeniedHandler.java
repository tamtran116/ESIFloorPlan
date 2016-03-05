package edu.umsl.esi.floorplan.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfToken;
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
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Tam Tran on 11/3/2015.
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private String errorPage;

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
        System.out.println(retval);


        System.out.println("<-------------------REQUEST HEADER------------------->");
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            System.out.println(headerName + ":" + request.getHeader(headerName));
        }

        System.out.println("Arrived in custom access denied handler.");
        HttpSession session = request.getSession();
        System.out.println("Session is " +session );
        System.out.println("Session id = " + session.getId());
        System.out.println("Session max interval="+session.getMaxInactiveInterval());
        System.out.println("Session last used="+session.getLastAccessedTime());
        System.out.println("Time now="+new Date().getTime());

        System.out.println();
        System.out.println("csrf:");
        Object csrf = request.getAttribute("_csrf");

        if (csrf==null) {
            System.out.println("csrf is null");
        } else {
            System.out.println(csrf.toString());
            if (csrf instanceof DefaultCsrfToken) {
                DefaultCsrfToken token = (DefaultCsrfToken) csrf;
                System.out.println("Parm name " + token.getParameterName());
                System.out.println("Token " + token.getToken());
            }

        }
        System.out.println();
        System.out.println("Request:");
        System.out.println(request.toString());
        System.out.println();
        System.out.println("Response:");
        System.out.println(response.toString());
        System.out.println();
        System.out.println("Exception:");
        System.out.println(accessDeniedException.toString());

        //do some business logic, then redirect to errorPage url
        response.sendRedirect(errorPage);

    }

}
