package com.service.begateway.filter;

import com.service.begateway.constant.Constant;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class RouteValidator {

    public Predicate<ServerHttpRequest> isSecure = request -> Constant.AUTH_WHITELIST.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
