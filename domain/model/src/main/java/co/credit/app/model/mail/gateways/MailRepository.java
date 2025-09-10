package co.credit.app.model.mail.gateways;

import co.credit.app.model.mail.Mail;
import reactor.core.publisher.Mono;

public interface MailRepository {
  Mono<String> sendMail(Mail mail);
}
