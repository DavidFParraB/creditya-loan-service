package co.credit.app.model.loantype.gateways;

import reactor.core.publisher.Mono;

public interface LoanTypeRepository {

    Mono<Boolean> isValidLoanType(Long loanTypeId);
}
