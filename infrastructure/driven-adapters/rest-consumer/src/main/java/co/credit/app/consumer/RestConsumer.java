package co.credit.app.consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import co.credit.app.consumer.dto.UserResponseDTO;
import co.credit.app.consumer.mapper.UserResponseDTOMapper;
import co.credit.app.model.user.User;
import co.credit.app.model.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements UserRepository {

        private final WebClient client;

        private final UserResponseDTOMapper userResponseDTOMapper;

        @Override
        @CircuitBreaker(name = "findUserByDocument")
        public Mono<User> findUserByDocument(String document) {
                return client
                                .get()
                                .uri(uriBuilder -> uriBuilder.path("/find-by-document/{document}")
                                                .build(document))
                                .retrieve().bodyToMono(UserResponseDTO.class)
                                .map(userResponseDTOMapper::toModel);
        }

}
