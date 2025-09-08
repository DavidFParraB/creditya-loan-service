package co.credit.app.api.config;

import co.credit.app.api.config.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
      AuthorizationFilter authorizationFilter) {
    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers(HttpMethod.GET, "/api/loan").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/loan/filter").permitAll()//.hasAnyRole("3")
            .pathMatchers(HttpMethod.POST, "/api/loan").hasAnyRole("2")
            .anyExchange().authenticated()
        )
        .addFilterAt(authorizationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}