package co.credit.app.sqs.listener;

import co.credit.app.sqs.listener.dto.LoanReceiverDTO;
import co.credit.app.usecase.loan.LoanUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final LoanUseCase loanUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        log.info("Processing message: {}", message.body());
        try {
          LoanReceiverDTO loanReceiverDTO = objectMapper.readValue(message.body(), LoanReceiverDTO.class);
          return loanUseCase.updateLoan(loanReceiverDTO.getLoan(), loanReceiverDTO.getStatus())
              .onErrorResume(e -> Mono.error(new IllegalArgumentException("Invalid loan or status.")))
              .then();
        } catch (Exception e) {
          log.error("Failed to deserialize message body", e);
          return Mono.error(e);
        }
    }
}
