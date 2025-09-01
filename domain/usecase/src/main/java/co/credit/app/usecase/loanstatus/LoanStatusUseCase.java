package co.credit.app.usecase.loanstatus;

import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanStatusUseCase {

    private final LoanTypeRepository loanTypeRepository;

    public Mono<Boolean> isValidLoanType(Long loanTypeId) {
        return loanTypeRepository.isValidLoanType(loanTypeId);
    }
}
