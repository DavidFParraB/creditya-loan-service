package co.credit.app.consumer;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import co.credit.app.consumer.dto.UserResponseDTO;
import co.credit.app.consumer.mapper.UserResponseDTOMapper;
import co.credit.app.model.user.User;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class RestConsumerTest {

    private MockWebServer mockBackEnd;

    @Mock
    private UserResponseDTOMapper userResponseDTOMapper;

    @InjectMocks
    private RestConsumer restConsumer;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        var webClient = WebClient.builder().baseUrl(mockBackEnd.url("/").toString()).build();
        restConsumer = new RestConsumer(webClient, userResponseDTOMapper);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (mockBackEnd != null) {
            mockBackEnd.shutdown();
        }
    }

    @Test
    @DisplayName("Validate the function findUserByDocument.")
    void validateFindUserByDocument() {
        String document = "1015435094";
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        User user = new User();
        user.setDocument(document);

        mockBackEnd.enqueue(new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(HttpStatus.OK.value())
                .setBody(
                        "{\"id\":6,\"name\":\"David\",\"email\":\"david.parra@mail.com\",\"document\":\"1015435094\",\"phone\":\"3143210987\",\"salary\":3200000.0,\"last_name\":\"Parra\",\"role_id\":1}")); // Add
                                                                                                                                                                                                              // appropriate
                                                                                                                                                                                                              // JSON
                                                                                                                                                                                                              // body
                                                                                                                                                                                                              // for
                                                                                                                                                                                                              // UserResponseDTO

        when(userResponseDTOMapper.toModel(any(UserResponseDTO.class))).thenReturn(user);

        Mono<User> result = restConsumer.findUserByDocument(document);

        StepVerifier.create(result)
                .expectNextMatches(objectResponse -> objectResponse.getDocument().equals(document))
                .verifyComplete();
    }
}