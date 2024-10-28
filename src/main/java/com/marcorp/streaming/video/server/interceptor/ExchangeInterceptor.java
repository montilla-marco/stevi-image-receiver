package com.marcorp.streaming.video.server.interceptor;


import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Component
public class ExchangeInterceptor implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String id = exchange.getRequest().getId();
//        System.out.println("request id = " + id);
//        RequestPath path = exchange.getRequest().getPath();
//        System.out.println("request path = " + path);
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().add(CONTENT_TYPE, "multipart/x-mixed-replace;boundary=123456789000000000000987654321");
//        HttpHeaders headers = response.getHeaders();
//        System.out.println("headers = " + headers);

        return chain.filter(exchange);
    }
}
