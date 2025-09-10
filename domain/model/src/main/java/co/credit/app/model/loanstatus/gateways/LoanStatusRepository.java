package co.credit.app.model.loanstatus.gateways;

import co.credit.app.model.loanstatus.LoanStatus;
import reactor.core.publisher.Mono;

public interface LoanStatusRepository {

  Mono<LoanStatus> getLoanStatusById(Long loanStatusId);

  Mono<LoanStatus> getLoanByName(String name);
}
