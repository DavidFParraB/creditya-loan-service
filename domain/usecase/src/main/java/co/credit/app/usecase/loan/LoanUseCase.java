package co.credit.app.usecase.loan;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {
    private final LoanRepository loanRepository;
    private final LoanTypeRepository loanTypeRepository;

    public Mono<Void> saveLoan(Loan loan) {

        return loanTypeRepository.isValidLoanType(loan.getLoandTypeId())
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)) {
                        return loanRepository.saveLoan(loan);
                    } else {
                        return Mono.error(new IllegalArgumentException("Invalid loan type."));
                    }
                });
    }

    public Flux<Loan> getAllLoans() {
        return loanRepository.getAllLoans();
    }
}
