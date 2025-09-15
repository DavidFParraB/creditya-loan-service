package co.credit.app.model.loantype.gateways;

import co.credit.app.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {

    Mono<LoanType> findById(Long loanTypeId);
}
