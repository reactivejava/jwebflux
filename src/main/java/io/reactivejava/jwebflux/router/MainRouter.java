package io.reactivejava.jwebflux.router;

import io.reactivejava.jwebflux.handler.MainHandler;
import io.reactivejava.jwebflux.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class MainRouter {
    @Autowired UserHandler userHandler;
    @Autowired MainHandler mainHandler;
    @Value("classpath:/html/index.html") Resource html;

    @Bean
    public RouterFunction<ServerResponse> route() {
        log.info("MainRouter init ...");
        return RouterFunctions
                .route(
                        GET("/"), request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
                )
                .andRoute(
                        POST("/add"), request -> {
                            Map<String, Object> attributes = request.attributes();
                            log.info(String.valueOf(attributes));
                            return ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(html);
                        }
                )
                .andRoute(
                        GET("/home").and(accept(MediaType.APPLICATION_JSON)),
                        mainHandler::main
                )
                .andRoute(
                        GET("/users").and(accept(MediaType.TEXT_HTML)),
                        request -> userHandler.users()
                );
    }
}
