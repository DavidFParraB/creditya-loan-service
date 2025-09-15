package co.credit.app.sqs.sender;

import co.credit.app.model.debtcapacity.DebtCapacity;
import co.credit.app.model.debtcapacity.gateways.DebtCapacityRepository;
import co.credit.app.model.mail.Mail;
import co.credit.app.model.mail.gateways.MailRepository;
import co.credit.app.sqs.sender.config.SQSSenderProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSenderDebCapacity implements DebtCapacityRepository {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Initialize ObjectMapper


  public Mono<String> send(String message) {
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.info("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }

  @Override
  public Mono<String> sendDebtCapacity(DebtCapacity debtCapacity) {
    try {
      String jsonMessage = objectMapper.writeValueAsString(debtCapacity);
      log.error("DebtCapacity to JSON : {} ", jsonMessage);
      return send(jsonMessage);
    } catch (JsonProcessingException e) {
      log.error("Failed to convert Mail to JSON", e);
      return Mono.error(e);
    }
  }
}
