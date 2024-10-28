package com.marcorp.streaming.video.server.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RestController
@RequestMapping(path = "/v1")
public class DummyController {

    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @GetMapping(path = "/dummy")//, produces = MediaType.TEXT_EVENT_STREAM_VALUE)//produces = "multipart/x-mixed-replace;boundary=123456789000000000000987654321")
    public Flux<byte[]> dummy() {
        return Flux.range(0, 20)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> i.toString().getBytes(StandardCharsets.UTF_8))
                .log();
    }
}
