package com.marcorp.streaming.video.server.config;

import com.marcorp.streaming.video.server.websocket.ServerHandler;
import com.marcorp.streaming.video.server.websocket.StreamingHandler;
import com.marcorp.streaming.video.server.reactor.VideoFixedSizeQueueOld;
import com.marcorp.streaming.video.server.websocket.VideoStreamingProtocolHandler;
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
    public VideoFixedSizeQueueOld<ByteBuffer> getVideoFixedSizeQueue() {
        return new VideoFixedSizeQueueOld<>(3000);
    }

    @Bean
    public BlockingQueue<ByteBuffer> videoQueue() {
        return new ArrayBlockingQueue<>(3000); // Ajusta la capacidad según sea necesario
    }
}
