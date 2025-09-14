package co.credit.app.usecase.loanreceiver;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanReceiverUseCase {

  public Mono<Void> processEvent(String eventData) {
    // Lógica de negocio para procesar el evento.
    // Aquí puedes deserializar el String a un objeto de dominio,
    // actualizar la base de datos, etc.
    return Mono.empty();
  }
}
