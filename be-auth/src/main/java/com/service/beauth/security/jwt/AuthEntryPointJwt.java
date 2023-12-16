package com.service.beauth.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.beauth.constant.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        log.error("Unauthorized error : {} ", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("responseCode", HttpServletResponse.SC_UNAUTHORIZED);
        bodyMap.put("ResponseMessage", "Unauthorized");
        List<String> stringList = new ArrayList<>();
        String error;
        if("Bad credentials".equals(authException.getMessage())) {
            error = Constant.Message.INVALID_LOGIN_MESSAGE;
        } else {
            error = authException.getMessage();
        }
        stringList.add(error);
        bodyMap.put("errorList", stringList);
        bodyMap.put("path", request.getServletPath());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), bodyMap);
    }
}