package co.credit.app.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapter-deb.sqs")
public record SQSSenderDebCapacityProperties(
     String region,
     String queueUrl,
     String endpoint){
}
