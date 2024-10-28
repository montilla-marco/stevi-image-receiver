package com.marcorp.streaming.video.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/git commit -m \"first commit\"")
public class HealthCheckController {

    @GetMapping()
    public Mono<String> healthCheck() {
        return Mono.just("OK");
    }
}