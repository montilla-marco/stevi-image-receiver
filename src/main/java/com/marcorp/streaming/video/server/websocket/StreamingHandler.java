package com.marcorp.streaming.video.server.websocket;

import com.marcorp.streaming.video.server.processor.VideoSenderProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class StreamingHandler implements WebSocketHandler {

    private final VideoSenderProcessor processor;

    public StreamingHandler(VideoSenderProcessor processor) {
        this.processor = processor;
    }


    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return processor.process(session);
    }
}
