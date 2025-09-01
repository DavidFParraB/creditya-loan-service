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

        // these methods are an example that illustrates the implementation of
        // WebClient.
        // You should use the methods that you implement from the Gateway from the
        // domain.
        @CircuitBreaker(name = "testGet" /* , fallbackMethod = "testGetOk" */)
        public Mono<ObjectResponse> testGet() {
                return client
                                .get()
                                .retrieve()
                                .bodyToMono(ObjectResponse.class);
        }

        // Possible fallback method
        // public Mono<String> testGetOk(Exception ignored) {
        // return client
        // .get() // TODO: change for another endpoint or destination
        // .retrieve()
        // .bodyToMono(String.class);
        // }

        @CircuitBreaker(name = "testPost")
        public Mono<ObjectResponse> testPost() {
                ObjectRequest request = ObjectRequest.builder()
                                .val1("exampleval1")
                                .val2("exampleval2")
                                .build();
                return client
                                .post()
                                .body(Mono.just(request), ObjectRequest.class)
                                .retrieve()
                                .bodyToMono(ObjectResponse.class);
        }

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
