package co.credit.app.api.config;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.credit.app.api.Handler;
import co.credit.app.api.RouterRest;
import co.credit.app.api.commons.ValidatorRequest;
import co.credit.app.api.mapper.LoanDTOMapper;
import co.credit.app.usecase.loan.LoanUseCase;
import reactor.core.publisher.Flux;

@ContextConfiguration(classes = { RouterRest.class, Handler.class })
@WebFluxTest
@Import({ CorsConfig.class, SecurityHeadersConfig.class })
class ConfigTest {

        @Autowired
        private WebTestClient webTestClient;

        @MockBean
        private LoanUseCase loanUseCase;

        @MockBean
        private LoanDTOMapper loanDTOMapper;

        @MockBean
        private ValidatorRequest validatorRequest;

        @Test
        void corsConfigurationShouldAllowOrigins() {
                when(loanUseCase.getAllLoans()).thenReturn(Flux.empty());

                webTestClient.get()
                                .uri("/api/loan")
                                .exchange()
                                .expectStatus().isOk()
                                .expectHeader().valueEquals("Content-Security-Policy",
                                                "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                                .expectHeader().valueEquals("Server", "")
                                .expectHeader().valueEquals("Cache-Control", "no-store")
                                .expectHeader().valueEquals("Pragma", "no-cache")
                                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
        }

}