package com.marcorp.streaming.video.server.config;

import com.marcorp.streaming.video.server.handler.ServerHandler;
import com.marcorp.streaming.video.server.handler.StreamingHandler;
import com.marcorp.streaming.video.server.reactor.VideoFixedSizeQueueOld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Configuration
public class ServerConfiguration {

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }


    @Bean
    public HandlerMapping handlerMapping(ServerHandler serverHandler, StreamingHandler streamingHandler) {
        Map<String, WebSocketHandler> handlerByPathMap = new HashMap<>();
        handlerByPathMap.put("/websocket", serverHandler);
        handlerByPathMap.put("/streaming", streamingHandler);
        int order = 1;

        return new SimpleUrlHandlerMapping(handlerByPathMap, order);
    }

    @Bean
    public VideoFixedSizeQueueOld<ByteBuffer> getVideoFixedSizeQueue() {
        return new VideoFixedSizeQueueOld<>(3000);
    }

    @Bean
    public BlockingQueue<ByteBuffer> videoQueue() {
        return new ArrayBlockingQueue<>(3000); // Ajusta la capacidad según sea necesario
    }
}
