package com.marcorp.streaming.video.server.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.nio.ByteBuffer;

public class VideoSubscription implements Subscription {

    private Subscriber subscriber;

    private final VideoFixedSizeQueueOld<ByteBuffer> videoQueue;
    private boolean isCancel;

    public VideoSubscription(Subscriber subscriber, VideoFixedSizeQueueOld<ByteBuffer> videoQueue) {
        this.subscriber = subscriber;
        this.videoQueue = videoQueue;
    }

    @Override
    public void request(long requested) {
        System.out.println("[VideoSubscription:request] requested : " + requested);
        if (isCancel) {
            return;
        }
        for (int i = 0; i < requested; i++) {
            var polled = videoQueue.poll();
//            System.out.println("polled = " + polled);
            if (null != polled) {
                this.subscriber.onNext(polled);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        Flux.range(0, Integer.getInteger("" + requested))
//                .takeWhile(test -> isCancel)
//                .map(index -> videoQueue.poll())
//                .filter(polled -> null != polled)
//                .doOnNext(polled -> this.subscriber.onNext(polled))
//                .delayElements(Duration.ofMillis(10))
//                .log()
//                .onErrorComplete();
    }

    @Override
    public void cancel() {
        System.out.println("[VideoSubscription:cancel] :");
        isCancel = true;
    }
}
