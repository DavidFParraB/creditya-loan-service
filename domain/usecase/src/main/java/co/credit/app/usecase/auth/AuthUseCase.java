package co.credit.app.usecase.auth;

import co.credit.app.model.auth.Auth;
import co.credit.app.model.auth.gateways.AuthRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase {

  private final AuthRepository authService;

  public Mono<Auth> validateToken(String token) {
    return authService.validateToken(token)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid token")));
  }
}
