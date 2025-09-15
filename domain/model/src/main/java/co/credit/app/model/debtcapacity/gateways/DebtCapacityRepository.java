package co.credit.app.model.debtcapacity.gateways;

import co.credit.app.model.debtcapacity.DebtCapacity;
import reactor.core.publisher.Mono;

public interface DebtCapacityRepository {

  Mono<String> sendDebtCapacity(DebtCapacity debtCapacity);

}
