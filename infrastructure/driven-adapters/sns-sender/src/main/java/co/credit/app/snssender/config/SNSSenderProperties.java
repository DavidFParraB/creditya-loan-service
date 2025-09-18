package co.credit.app.snssender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapter.sns")
public record SNSSenderProperties(
     String region,
     String topicArn){
}
