package co.credit.app.snssender;

import co.credit.app.model.mail.Mail;
import co.credit.app.model.mail.gateways.MailRepository;
import co.credit.app.snssender.config.SNSSenderProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ExecutionException;
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
public class SNSSender implements MailRepository {

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
  public Mono<String> sendMail(Mail mail) {
    try {
      String jsonMessage = objectMapper.writeValueAsString(mail);
      log.error("Mail to JSON : {} ", jsonMessage);
      return send(jsonMessage);
    } catch (JsonProcessingException e) {
      log.error("Failed to convert Mail to JSON", e);
      return Mono.error(e);
    }
  }

}
