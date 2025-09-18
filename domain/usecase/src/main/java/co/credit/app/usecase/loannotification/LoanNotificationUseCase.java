package co.credit.app.usecase.loannotification;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loannotification.LoanNotification;
import co.credit.app.model.loannotification.gateways.LoanNotificationRepository;
import co.credit.app.model.loantype.LoanType;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanNotificationUseCase {
  private final LoanNotificationRepository loanNotificationRepository;

  public Mono<Void> sendNotification(Loan loan, LoanType loanType, String status) {
    LoanNotification loanNotification = LoanNotification.builder()
        .loan(loan.getId())
        .email(loan.getEmail())
        .status(status)
        .amount(loan.getAmount())
        .term(loan.getTerm())
        .rate(loanType.getInterestRate())
        .build();
    return loanNotificationRepository.sendLoanNotification(loanNotification).then();
  }
}
