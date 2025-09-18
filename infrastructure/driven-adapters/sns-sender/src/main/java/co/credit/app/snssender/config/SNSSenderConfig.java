package co.credit.app.snssender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;

@Configuration
public class SNSSenderConfig {

  @Bean
  public SnsAsyncClient snsAsyncClient(SNSSenderProperties properties) {
    return SnsAsyncClient.builder()
        .credentialsProvider(DefaultCredentialsProvider.create())
        .region(Region.of(properties.region()))
        .build();
  }
}