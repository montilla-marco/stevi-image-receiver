package com.marcorp.streaming.video.server.processor;

import com.marcorp.streaming.video.server.reactor.WebSocketVideoPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class VideoSenderProcessorOld {

    private final WebSocketVideoPublisher publisher;

    public VideoSenderProcessorOld(WebSocketVideoPublisher publisher) {
        this.publisher = publisher;
    }

    public Mono<Void> process(WebSocketSession session) {
        System.out.println("[VideoSenderProcessorOld:process] session : " + session);
        return session.send(publisher).log().then();
    }
//        Flux<WebSocketMessage> messages =
//                Flux.from(publisher)
//                        .map(message -> session.binaryMessage((factory) -> factory.wrap(message)))
//                        .doOnNext(webSocketMessage -> {
//
//                        });
//        session.
//        (message -> {
//
//            if (newClient.get()) {
//                System.out.println("Receiver -> client connected id= " + session.getId());
//            }
//        })
//        return Flux.from(publisher)
//                .doOnNext(message -> {
//                    session.send(Mono.fromCallable(() -> session.binaryMessage((factory) -> factory.wrap(message))));
//                    System.out.println("Server -> sent: [" +  message + "] to client id=[" + session.getId() + "] ");
//                }).then();
//    }

//    private Flux<Void> sendAtInterval(WebSocketSession session, long interval) {
//        return Flux.empty();
//        return Flux.from(publisher).flatMap(message ->
//                        session.send(Mono.fromCallable(() -> session.binaryMessage((factory) -> factory.wrap(message))))
//                                .log()
//                                .then(
//                                        Mono.fromRunnable(() -> System.out.println("Server -> sent: [" +  message + "] to client id=[" + session.getId() + "] " ))
//                                ));



//                Flux.interval(Duration.ofMillis(interval))
//                        .map(value -> Long.toString(value))
//                        .flatMap(message ->
//                                session
//                                        .send(Mono.fromCallable(() -> session.textMessage(message)))
//                                        .log()
//                                        .then(
//                                                Mono.fromRunnable(() -> System.out.println("Server -> sent: [" +  message + "] to client id=[" + session.getId() + "] " ))
//                                        )
//                        );
//    }

}
