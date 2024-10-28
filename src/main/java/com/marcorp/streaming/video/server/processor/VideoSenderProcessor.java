package com.marcorp.streaming.video.server.processor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

@Component
public class VideoSenderProcessor {

    private final BlockingQueue<ByteBuffer> queue;

    public VideoSenderProcessor(BlockingQueue<ByteBuffer> queue) {
        this.queue = queue;
    }

    public Mono<Void> process(WebSocketSession session) {
        System.out.println("[VideoSenderProcessor:process] session : " + session);

        return Mono.<Void>fromRunnable(() -> {
            while (session.isOpen()) {
                try {
                    ByteBuffer message = queue.take(); // Esperar a que haya un mensaje disponible
                    session.send(Mono.just(session.binaryMessage(factory -> factory.wrap(message))))
                            .doOnSuccess(aVoid -> System.out.println("Server -> sent: [" + message.remaining() + " bytes] to client id=[" + session.getId() + "]"))
                            .subscribe();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break; // Salir si se interrumpe
                }
            }
        }).then();
    }
}

