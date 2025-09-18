package co.credit.app.snssender;

import co.credit.app.model.loannotification.LoanNotification;
import co.credit.app.model.loannotification.gateways.LoanNotificationRepository;
import co.credit.app.snssender.config.SNSSenderProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@RequiredArgsConstructor
@Log4j2
@Service
public class SNSSender implements LoanNotificationRepository {

  private final SnsAsyncClient snsAsyncClient;
  private final SNSSenderProperties snsSenderProperties;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public Mono<String> send(String message) {
    return Mono.fromFuture(snsAsyncClient.publish(buildRequest(message)))
        .map(PublishResponse::messageId);
  }

  private PublishRequest buildRequest(String message) {
    return PublishRequest.builder().message(message).topicArn(snsSenderProperties.topicArn())
        .build();
  }

  @Override
  public Mono<String> sendLoanNotification(LoanNotification loanNotification) {

    return Mono.fromCallable(() -> objectMapper.writeValueAsString(loanNotification))
        .doOnNext(jsonMessage -> log.info("LoanNotification to JSON: {}", jsonMessage))
        .flatMap(this::send)
        .onErrorResume(JsonProcessingException.class, e -> {
          log.error("Failed to convert LoanNotification to JSON", e);
          return Mono.error(e);
        })
        .onErrorResume(e -> {
          log.error("Failed to send Loan Notification to SNS", e);
          return Mono.just("Failed to send Loan Notification to SNS");
        });
  }

}
