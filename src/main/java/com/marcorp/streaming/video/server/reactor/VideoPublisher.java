package com.marcorp.streaming.video.server.reactor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class VideoPublisher {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public void addSession(WebSocketSession session) {
        sessions.add(session);
        // Almacenar el cierre de la sesión usando doOnCancel
        session.receive()
                .doOnNext(message -> {})
                .doOnCancel(() -> sessions.remove(session))
                .subscribe();
    }

    public void sendFrame(ByteBuffer frame) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.send(Mono.just(session.textMessage("Frame size: " + frame.remaining())))
                        .subscribe();
            }
        }
    }
}
