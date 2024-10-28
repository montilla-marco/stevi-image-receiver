package com.marcorp.streaming.video.server.client;

import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.socket.WebSocketMessage;
import java.net.URI;
import java.time.Duration;

public class ReactiveJavaClientWebSocket {

    public static void main(String[] args) throws InterruptedException {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                        URI.create("ws://localhost:9000/websocket"),
                        session -> session.send(
                                        Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
                                .thenMany(session.receive()
                                        .map(WebSocketMessage::getPayloadAsText)
                                        .doOnNext(message -> {
                                            System.out.println("Server -> received from client id=["+ session.getId() + "]: [" + message.getClass() + "]");
                                        })
                                        .log())
                                .then())
                .block(Duration.ofSeconds(10L));
    }
}
