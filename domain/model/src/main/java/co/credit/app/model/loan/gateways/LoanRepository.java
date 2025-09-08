package co.credit.app.model.loan.gateways;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loanfilter.LoanFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRepository {

    Mono<Void> saveLoan(Loan loan);

    Flux<Loan> getAllLoans();

    Flux<Loan> getAllLoansWithPagination(LoanFilter filter);
}
