package com.marcorp.streaming.video.server.processor;

import com.marcorp.streaming.video.server.reactor.VideoPublisher;
import io.netty.buffer.ByteBuf;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;

@Component
public class VideoReceiverProcessor {

    private final BlockingQueue<ByteBuffer> queue;
    private final VideoPublisher publisher; // Para enviar frames a navegadores
    private boolean newClient = true;

    public VideoReceiverProcessor(BlockingQueue<ByteBuffer> queue, VideoPublisher publisher) {
        this.queue = queue;
        this.publisher = publisher;
        startProcessing();
    }

    private void startProcessing() {
        Thread processingThread = new Thread(() -> {
            try {
                while (true) {
                    ByteBuffer frame = queue.take(); // Espera a que haya un frame disponible
                    processFrame(frame); // Procesa el frame
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        processingThread.start();
    }

    public Mono<Void> process(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayload)
                .map(payload -> toNioBuffer((NettyDataBuffer) payload))
                .doOnNext(frame -> {
                    queue.offer(frame);
                    System.out.println("Received frame of size: " + frame.remaining());
                })
                .doOnNext(message -> {
                    if (newClient) {
                        System.out.println("New client connected: " + session.getId());
                        newClient = false;
                    }
                })
                .then();
    }

    private void processFrame(ByteBuffer frame) {
        // Envía el frame a todos los navegadores conectados
        publisher.sendFrame(frame);
        System.out.println("Processed frame of size: " + frame.remaining());
    }

    // Método para convertir NettyDataBuffer a ByteBuffer
    public static ByteBuffer toNioBuffer(NettyDataBuffer nettyDataBuffer) {
        ByteBuf buffer = nettyDataBuffer.getNativeBuffer();
        return buffer.isDirect() ? buffer.nioBuffer() : ByteBuffer.wrap(buffer.array());
    }
}
