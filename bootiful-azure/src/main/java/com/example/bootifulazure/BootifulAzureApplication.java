package com.example.bootifulazure;

import com.microsoft.azure.servicebus.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableBinding({Source.class, Sink.class})
@SpringBootApplication
public class BootifulAzureApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulAzureApplication.class, args);
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}


@Log4j2
@RestController
@RequiredArgsConstructor
class SpringCloudStreamServiceBusDemo {

    private final Source source;

    @GetMapping("/send")
    void send() {
        this.source.output().send(MessageBuilder.withPayload("Hello bootiful service bus @ " + Instant.now() + "!").build());
    }

    @StreamListener(Sink.INPUT)
    public void incoming(org.springframework.messaging.Message<?> message) {
        log.info("new message: " + message.getPayload());
        message.getHeaders().forEach((k, v) -> {
            log.info(k + '=' + v);
        });
    }
}