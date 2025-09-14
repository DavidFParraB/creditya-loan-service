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
            .pathMatchers("/v3/api-docs/**").permitAll()
            .pathMatchers("/webjars/**").permitAll()
            .pathMatchers("/swagger-ui.html").permitAll()
            .pathMatchers("/swagger-ui/**").permitAll()

            .pathMatchers(HttpMethod.GET, "/api/loan").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/loan/filter").hasAnyRole("3")
            .pathMatchers(HttpMethod.POST, "/api/loan").hasAnyRole("2")
            //.pathMatchers(HttpMethod.PUT, "/api/loan/{id}").permitAll()
            .pathMatchers(HttpMethod.PUT, "/api/loan/{id}").hasAnyRole("3")
            .anyExchange().authenticated()
        )
        .addFilterAt(authorizationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}