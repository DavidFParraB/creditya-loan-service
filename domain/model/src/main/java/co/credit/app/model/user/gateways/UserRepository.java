package co.credit.app.model.user.gateways;

import co.credit.app.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> findUserByDocument(String document);
}
