package io.reactivejava.jwebflux.handler;

import io.reactivejava.jwebflux.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class UserHandler implements HandlerFunction<ServerResponse> {
    private static final Set<User> cache = new HashSet<>();

    static {
        for (int i = 0; i < 10; i++) {
            User user = new User("TestName" + i, "TestCountry" + i);
            cache.add(user);
        }
    }

    public Mono<ServerResponse> users() {
        return ServerResponse.ok().body(BodyInserters.fromValue(cache));
    }
//
//    public Mono<User> add(User user) {
//        log.info("Add user: " + user);
//        cache.add(user);
//        return Mono.just(user);
//    }
//
//    public Mono<User> remove(User user) {
//        log.info("Remove user: " + user);
//        cache.remove(user);
//        return Mono.just(user);
//    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.ok().body(BodyInserters.fromValue(cache));
    }
}
