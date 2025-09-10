package co.credit.app.model.loanstatus.gateways;

import reactor.core.publisher.Mono;

public interface LoanStatusRepository {

  Mono<Boolean> isValidLoanStatus(Long loanStatusId);
}
