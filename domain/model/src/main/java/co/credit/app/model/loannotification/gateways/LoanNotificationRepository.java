package co.credit.app.model.loannotification.gateways;

import co.credit.app.model.loannotification.LoanNotification;
import reactor.core.publisher.Mono;

public interface LoanNotificationRepository {
  Mono<String> sendLoanNotification(LoanNotification loanNotification);
}
