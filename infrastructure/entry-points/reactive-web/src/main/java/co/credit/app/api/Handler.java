package co.credit.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import co.credit.app.model.loan.Loan;
import co.credit.app.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
  private final LoanUseCase loanUseCase;

  public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
    return ServerResponse.ok().body(loanUseCase.getAllLoans(), Loan.class);
  }

  public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(Loan.class).flatMap(loan -> loanUseCase.saveLoan(loan))
        .then(ServerResponse.status(HttpStatus.OK).build())
        .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }
}
