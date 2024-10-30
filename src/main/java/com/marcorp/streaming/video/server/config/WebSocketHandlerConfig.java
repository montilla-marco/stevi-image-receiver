package com.marcorp.streaming.video.server.config;

import com.marcorp.streaming.video.server.websocket.ServerHandler;
import com.marcorp.streaming.video.server.websocket.StreamingHandler;
import com.marcorp.streaming.video.server.websocket.VideoStreamingProtocolHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketHandlerConfig {

    @Bean
    public HandlerMapping handlerMapping(VideoStreamingProtocolHandler streamingProtocolHandler) {
        Map<String, WebSocketHandler> handlerByPathMap = new HashMap<>();
        handlerByPathMap.put("/chat", streamingProtocolHandler);
//        handlerByPathMap.put("/websocket", serverHandler);
//        handlerByPathMap.put("/streaming", streamingHandler);
        int order = 1;
        return new SimpleUrlHandlerMapping(handlerByPathMap, order);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
