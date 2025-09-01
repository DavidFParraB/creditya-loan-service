package co.credit.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.commons.ValidatorRequest;
import co.credit.app.api.dto.LoanDTO;
import co.credit.app.api.dto.SuccessResponse;
import co.credit.app.api.mapper.LoanDTOMapper;
import co.credit.app.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

  private final LoanUseCase loanUseCase;
  private final LoanDTOMapper loanDTOMapper;
  private final ValidatorRequest validatorRequest;

  public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
    return loanUseCase.getAllLoans()
        .map(loanDTOMapper::toResponse)
        .collectList()
        .flatMap(loanDTOs -> ServerResponse.ok().bodyValue(loanDTOs))
        .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(LoanDTO.class)
        .flatMap(validatorRequest::validate)
        .flatMap(loanDTO -> loanUseCase.saveLoan(loanDTOMapper.toModel(loanDTO))
            .then(ServerResponse.status(HttpStatus.OK).bodyValue(new SuccessResponse(0, "OK"))));

  }

}