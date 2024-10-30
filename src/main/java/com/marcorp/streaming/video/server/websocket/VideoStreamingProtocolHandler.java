package com.marcorp.streaming.video.server.websocket;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;

@Component
public class VideoStreamingProtocolHandler implements WebSocketHandler {

    // Sinks.Many es una clase de Reactor que permite manejar flujos de datos.
    private final Sinks.Many<String> sink;

    private final SessionManager sessionManager;

    public VideoStreamingProtocolHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Override
    public List<String> getSubProtocols() {
        return WebSocketHandler.super.getSubProtocols();
    }
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        // Extraer el username de la URI
//        String nickname = UriComponentsBuilder.fromUri(session.getHandshakeInfo().getUri())
//                .build()
//                .getQueryParams()
//                .getFirst("username");
//
//        // Añadir la sesión al SessionManager
//        sessionManager.addSession(nickname, session);
//
//        // Suscribirse a los mensajes del flujo y enviarlos a los clientes conectados
//        Mono<Void> output = session.send(
//                sink.asFlux().map(session::textMessage)
//        );
//
//        // Recibir mensajes de los clientes y emitirlos al flujo
//        Mono<Void> input = session.receive()
//                .map(msg -> nickname + ": " + msg.getPayloadAsText())
//                .doOnNext(sink::tryEmitNext)
//                .then();
//
//        return Mono.zip(input, output).then()
//                .doFinally(sig -> sessionManager.removeSession(nickname));

//        // Añadir la sesión al SessionManager
//        sessionManager.addSession(username, session);
//
//        // Manejar mensajes recibidos y enviar respuestas
//        return session.send(
//                session.receive()
//        ).doFinally(sig -> {
//            // Remover la sesión del SessionManager cuando se cierre la conexión
//            sessionManager.removeSession(session.getId());
//            System.out.println("Connection Closed: " + session.getId());
//        }).doOnTerminate(() -> {
//            System.out.println("Connection Established: " + session.getId());
//        });
//    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                session.receive()
                        .map(msg -> session.textMessage("Echo: " + msg.getPayloadAsText()))
        );
    }
}
