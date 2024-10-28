package com.marcorp.streaming.video.server.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;

import java.nio.ByteBuffer;

@Component
public class WebSocketVideoPublisher implements Publisher<WebSocketMessage> {

    private final VideoFixedSizeQueueOld<ByteBuffer> videoQueue;

    public WebSocketVideoPublisher(VideoFixedSizeQueueOld<ByteBuffer> videoQueue) {
        this.videoQueue = videoQueue;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        var videoSubscription = new WebSocketVideoSubscription(subscriber, videoQueue);
        subscriber.onSubscribe(videoSubscription );
    }

}
