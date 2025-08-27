package co.credit.app.api;

import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import co.credit.app.model.loan.Loan;
import co.credit.app.usecase.loan.LoanUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private LoanUseCase loanUseCase;

  /*@Test
  void testListenGETUseCase() {

    when(loanUseCase.getAllLoans()).thenReturn(Flux.just(getLoans()));

    webTestClient.get().uri("/api/loan").accept(MediaType.APPLICATION_JSON).exchange()
        .expectStatus().isOk().expectBody(Loan.class).value(userResponse -> {
          Assertions.assertThat(userResponse).actual();
        });
  }*/

  Loan getLoans() {
    Loan loandTest = new Loan();
    return loandTest;

  }


  /*@Test
  void testListenPOSTUseCase() {
    webTestClient.post().uri("/api/loan").accept(MediaType.APPLICATION_JSON).bodyValue("")
        .exchange().expectStatus().isOk().expectBody(String.class).value(userResponse -> {
          Assertions.assertThat(userResponse).isEmpty();
        });
  }*/
}
