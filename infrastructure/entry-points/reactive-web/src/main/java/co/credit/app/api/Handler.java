package co.credit.app.api;

import co.credit.app.api.dto.LoanFilterDTO;
import co.credit.app.api.mapper.LoanFilterDTOMapper;
import co.credit.app.api.mapper.LoanReportDTOMapper;
import co.credit.app.usecase.auth.AuthUseCase;
import co.credit.app.usecase.loanreport.LoanReportUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.credit.app.api.commons.ValidationError;
import co.credit.app.api.commons.ValidatorRequest;
import co.credit.app.api.dto.ErrorResponse;
import co.credit.app.api.dto.LoanDTO;
import co.credit.app.api.dto.SuccessResponse;
import co.credit.app.api.mapper.LoanDTOMapper;
import co.credit.app.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class Handler {

  private final LoanUseCase loanUseCase;
  private final LoanDTOMapper loanDTOMapper;
  private final ValidatorRequest validatorRequest;
  private final AuthUseCase authUseCase;
  private final LoanReportDTOMapper loanReportDTOMapper;
  private final LoanFilterDTOMapper loanFilterDTOMapper;
  private final LoanReportUseCase loanReportUseCase;

  public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
    return loanUseCase.getAllLoans()
        .map(loanDTOMapper::toResponse)
        .collectList()
        .flatMap(loanDTOs -> ServerResponse.ok().bodyValue(loanDTOs))
        .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  public Mono<ServerResponse> listenPOSTByFilterUseCase(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(LoanFilterDTO.class)
        .flatMap(validatorRequest::validate)
        .flatMap(loanFilterDTO -> loanReportUseCase.generateLoanReport(loanFilterDTOMapper.toModel(loanFilterDTO))
            .map(loanReportDTOMapper::toResponse)
            .collectList()
            .flatMap(loanDTOs -> ServerResponse.ok().bodyValue(loanDTOs))
            .doOnNext(loan -> log.info("Loan report generated with filter: {}", loan)))
        .onErrorResume(ValidationError.class, e -> ServerResponse.badRequest()
            .bodyValue(new ErrorResponse(e.getMessage(), e.getErrors())));
  }

  public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
    String authHeader = serverRequest.headers().firstHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);

      return authUseCase.validateToken(token)
          .flatMap(authResult -> serverRequest.bodyToMono(LoanDTO.class)
              .flatMap(validatorRequest::validate)
              .flatMap(loanDTO -> {
                String subject = authResult.getUsername();
                if (subject.equals(loanDTO.getEmail())) {
                  return loanUseCase.saveLoan(loanDTOMapper.toModel(loanDTO))
                      .then(ServerResponse.status(HttpStatus.OK)
                          .bodyValue(new SuccessResponse(0, "OK")));
                } else {
                  return ServerResponse.status(HttpStatus.FORBIDDEN)
                      .bodyValue(new ErrorResponse("User mismatch",
                          List.of("The token subject does not match the user in the request")));
                }
              })
          )
          .onErrorResume(ValidationError.class, e -> ServerResponse.badRequest()
              .bodyValue(new ErrorResponse(e.getMessage(), e.getErrors())))
          .doOnNext(loan -> log.info("Loan saved: {}", loan));
    } else {
      return ServerResponse.status(HttpStatus.UNAUTHORIZED)
          .bodyValue(new ErrorResponse("Unauthorized",
              List.of("Authorization header is missing or invalid")));
    }
  }
}