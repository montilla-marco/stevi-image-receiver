package com.marcorp.streaming.video.server.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;

import java.nio.ByteBuffer;

public class WebSocketVideoSubscription implements Subscription {

    private Subscriber subscriber;

    private final DefaultDataBufferFactory dataBufferFactory;
    private final VideoFixedSizeQueueOld<ByteBuffer> videoQueue;
    private boolean isCancel;

    public WebSocketVideoSubscription(Subscriber subscriber, VideoFixedSizeQueueOld<ByteBuffer> videoQueue) {
        this.subscriber = subscriber;
        this.videoQueue = videoQueue;
        dataBufferFactory = new DefaultDataBufferFactory();
    }

    @Override
    public void request(long requested) {
        System.out.println("[WebSocketVideoSubscription:request] requested : " + requested);
        if (isCancel) {
            return;
        }
        for (int i = 0; i < requested; i++) {
            var polled = videoQueue.poll();
//            System.out.println("polled = " + polled);
            if (null != polled) {
                DefaultDataBuffer dataBuffer = dataBufferFactory.wrap(polled);
                WebSocketMessage message = new WebSocketMessage(WebSocketMessage.Type.BINARY, dataBuffer);
                this.subscriber.onNext(message);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void cancel() {
        System.out.println("[WebSocketVideoSubscription:cancel] :");
        isCancel = true;
    }
}
