package com.marcorp.streaming.video.server.rest;

import com.marcorp.streaming.video.server.model.Coordinate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping(path = "/coordinates")
public class GPSController {
    private final AtomicReference<Coordinate> latestCoordinate = new AtomicReference<>();

    @PostMapping
    public Mono<Integer> receiveCoordinates(@RequestBody Coordinate coordinate) {
        System.out.println("[GPSController:getCoordinates] : in");
        latestCoordinate.set(coordinate);
        return Mono.justOrEmpty(1).log();
    }

    @GetMapping
    public Mono<Coordinate> getCoordinates() {
        System.out.println("[GPSController:getCoordinates] : out");
        Coordinate coordinate = latestCoordinate.get();
        return Mono.justOrEmpty(coordinate).log();
    }
}
