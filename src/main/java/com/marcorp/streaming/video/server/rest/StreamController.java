package com.marcorp.streaming.video.server.rest;

import com.marcorp.streaming.video.server.reactor.VideoPublisherOldm;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RestController
@RequestMapping(path = "/stream")
public class StreamController {

    private final VideoPublisherOldm videoPublisher;
    private final Sinks.Many<ByteBuffer> sink;

    private boolean isCompleted = false; // Variable para rastrear el estado de completitud

    public StreamController(VideoPublisherOldm videoPublisher) {
        this.videoPublisher = videoPublisher;
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    private void startStreaming() {
        videoPublisher.subscribe(new Subscriber<ByteBuffer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE); // Solicitar todos los datos disponibles
            }

            @Override
            public void onNext(ByteBuffer byteBuffer) {
                if (!isCompleted) {
                    sink.tryEmitNext(byteBuffer);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (!isCompleted) {
                    sink.tryEmitError(throwable);
                    isCompleted = true; // Marcar como completado al haber un error
                }
            }

            @Override
            public void onComplete() {
                if (!isCompleted) {
                    sink.tryEmitComplete();
                    isCompleted = true; // Marcar como completado
                }
            }
        });
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Flux<ByteBuffer> stream() {
        return Flux.from(videoPublisher).log();
    }

//        Flux<ByteBuffer> byteBuffers =
//                Flux.from(publisher).log();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
//        headers.add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        headers.add(TRANSFER_ENCODING, "chunked");
//        headers.add("X-framerate", "60");
//
//        return Mono.just(ResponseEntity.ok()
//                .headers(headers)
//                .body(byteBuffers));
//        Flux.from(publisher)
//                .map(buffer -> ResponseEntity.ok()
//                    .header(CONTENT_TYPE, "multipart/x-mixed-replace;boundary=123456789000000000000987654321")
//                    .header(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
//                    .header(TRANSFER_ENCODING, "chunked")
//                    .header("X-framerate", "60")
//                .body(list))
//    public ResponseEntity<Flux<byte[]>> stream() {
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .header(CONTENT_TYPE, "multipart/x-mixed-replace;boundary=123456789000000000000987654321")
//                .header(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
//                .header(TRANSFER_ENCODING, "chunked")
//                .header("X-framerate", "60")
//                .body(Flux.from(publisher).map(data -> data.array()));
       //return Flux.interval(Duration.ofMillis(100)).from(publisher).log();
   // }

    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @GetMapping(path = "/dummy")//, produces = MediaType.TEXT_EVENT_STREAM_VALUE)//produces = "multipart/x-mixed-replace;boundary=123456789000000000000987654321")
    public Flux<byte[]> dummy() {
        //public Mono<ResponseEntity<Flux<byte[]>>> dummy() {
//        Flux<byte[]> log = Flux.fromArray(new String[]{"Uno", "Dos", "Tres", "Uno", "Dos", "Tres", "Uno", "Dos", "Tres"})
//                .map(numberStr -> numberStr.getBytes(StandardCharsets.UTF_8))
//                .log();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(CONTENT_TYPE, "multipart/x-mixed-replace;boundary=123456789000000000000987654321");
//        headers.add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//        headers.add(TRANSFER_ENCODING, "chunked");
//        headers.add("X-framerate", "60");
//
//        return Mono.just(ResponseEntity.ok()
//                .headers(headers)
//                .body(log));

//        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
//
//        HttpHeaders metadataHeaders = new HttpHeaders();
//        metadataHeaders.setContentType(MediaType.APPLICATION_JSON);
//        formData.add("metadata", new HttpEntity<>("{}", metadataHeaders));

//        HttpHeaders contentHeaders = new HttpHeaders();
//        contentHeaders.setContentType(MediaType.IMAGE_JPEG);
//        formData.add("contents", new HttpEntity<>("Uno".getBytes(), contentHeaders));


        //Flux<Map<String, HttpEntity>> contents = Flux.range(0, 20)
        return Flux.range(0, 20)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> {
                    return i.toString().getBytes(StandardCharsets.UTF_8);
//                    Map<String, HttpEntity> formData = new HashMap<>();
//                    HttpHeaders contentHeaders = new HttpHeaders();
//                    contentHeaders.setContentType(MediaType.IMAGE_JPEG);
//                    formData.put("contents", new HttpEntity<>("Uno".getBytes(), contentHeaders));
//                    return formData;
                })
                .log();
//        HttpHeaders headers = HttpHeaders.writableHttpHeaders(HttpHeaders.EMPTY);
//        headers.add("Content-Type", "multipart/x-mixed-replace; boundary=--icecream");
//        return ResponseEntity.status(200).headers(headers).body(contents);
    }
}