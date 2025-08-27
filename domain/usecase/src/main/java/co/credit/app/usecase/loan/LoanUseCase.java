package co.credit.app.usecase.loan;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class LoanUseCase {
    private final LoanRepository loanRepository;

    public Mono<Void> saveLoan(Loan loan) {
        return loanRepository.saveLoan(loan);
    }

    public Flux<Loan> getAllLoans() {
        return loanRepository.getAllLoans();
    }
}
