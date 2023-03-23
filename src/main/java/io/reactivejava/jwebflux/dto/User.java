package io.reactivejava.jwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
public class User implements ServerResponse {
    String name;
    String country;

    @Override
    public HttpStatusCode statusCode() {
        return HttpStatusCode.valueOf(202);
    }

    @Override
    public int rawStatusCode() {
        return 0;
    }

    @Override
    public HttpHeaders headers() {
        return HttpHeaders.EMPTY;
    }

    @Override
    public MultiValueMap<String, ResponseCookie> cookies() {
        return null;
    }

    @Override
    public Mono<Void> writeTo(ServerWebExchange exchange, Context context) {
        return null;
    }
}
