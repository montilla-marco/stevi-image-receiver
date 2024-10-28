package com.marcorp.streaming.video.server.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class VideoPublisherOldm implements Publisher<ByteBuffer> {

    private final VideoFixedSizeQueueOld<ByteBuffer> videoQueue;

    public VideoPublisherOldm(VideoFixedSizeQueueOld<ByteBuffer> videoQueue) {
        this.videoQueue = videoQueue;
    }

    @Override
    public void subscribe(Subscriber<? super ByteBuffer> subscriber) {
        var videoSubscription = new VideoSubscription(subscriber, videoQueue);
        subscriber.onSubscribe(videoSubscription );
    }


}
