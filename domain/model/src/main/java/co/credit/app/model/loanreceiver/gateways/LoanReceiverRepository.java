package co.credit.app.model.loanreceiver.gateways;

import co.credit.app.model.loanreceiver.LoanReceiver;
import reactor.core.publisher.Mono;

public interface LoanReceiverRepository {
  Mono<LoanReceiver> getLoanReceiver();
}
