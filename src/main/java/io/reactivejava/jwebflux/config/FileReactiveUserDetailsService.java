package io.reactivejava.jwebflux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static io.reactivejava.jwebflux.config.AppConfig.AUTH_FILE;

@Slf4j
public class FileReactiveUserDetailsService implements ReactiveUserDetailsService, InitializingBean {
    static final Properties storage = new Properties();

    @Value("${" + AUTH_FILE + "}")
    private String authFile;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String pass = storage.getProperty(username);

        if (pass == null)
            return Mono.empty();

        User user = new User(
                username,
                pass,
                Collections.singleton((GrantedAuthority) () -> "ROLE_ADMIN")
        );

        return Mono.just(user);
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        log.info("FileReactiveUserDetailsService afterPropertiesSet ...");
        List<String> lines = Files.readAllLines(Path.of(authFile));

        for (String line: lines) {
            String[] creds = line.split("=");

            if (creds.length != 2)
                throw new RuntimeException("User format should be 'login=password' type.");

            storage.put(creds[0], creds[1]);
        }
    }
}
