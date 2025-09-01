package co.credit.app.usecase.loan;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import co.credit.app.model.user.gateways.UserRepository;
import co.credit.app.usecase.utils.Constants;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {
    private final LoanRepository loanRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final UserRepository userRepository;

    public Mono<Void> saveLoan(Loan loan) {

        return loanTypeRepository.isValidLoanType(loan.getLoanTypeId())
                .flatMap(exist -> {
                    if (Boolean.TRUE.equals(exist)) {

                        return userRepository.findUserByDocument(loan.getDocument())
                                .flatMap(user -> {
                                    loan.setStatusId(Constants.LOAN_STATUS_PENDING);
                                    return loanRepository.saveLoan(loan);
                                }).then()
                                .onErrorResume(e -> Mono.error(new IllegalArgumentException("Invalid User.")));

                    } else {
                        return Mono.error(new IllegalArgumentException("Invalid loan type."));
                    }
                });
    }

    public Flux<Loan> getAllLoans() {
        return loanRepository.getAllLoans();
    }
}
