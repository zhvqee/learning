package com.qee.gateway;

import com.qee.gateway.annotions.EnableGateWay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;


@SpringBootApplication
@EnableGateWay
public class GateWayApplication {

    public static void main(String[] args) {
        CompletableFuture.completedFuture()
        SpringApplication.run(GateWayApplication.class, args);
    }
}
