package com.marcorp.streaming.video.server.handler;

import com.marcorp.streaming.video.server.processor.VideoReceiverProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class ServerHandler implements WebSocketHandler {

    private final long interval;

    private final VideoReceiverProcessor videoReceiverProcessor;
    public ServerHandler(VideoReceiverProcessor videoReceiverProcessor) {
        this.videoReceiverProcessor = videoReceiverProcessor;
        this.interval = 100L;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return videoReceiverProcessor.process(session);
    }
}