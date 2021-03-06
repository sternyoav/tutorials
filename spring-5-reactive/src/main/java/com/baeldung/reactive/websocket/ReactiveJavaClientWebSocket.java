package com.baeldung.reactive.websocket;

import java.net.URI;
import java.time.Duration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveJavaClientWebSocket {
    public static void main(String[] args) throws InterruptedException {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create("ws://localhost:8080/event-emitter"), session ->  session.send(Mono.just(session.textMessage("event-me-from-spring-reactive-client")))
            .thenMany(session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .log())
            .then())
            .block(Duration.ofSeconds(10L));
    }
}
