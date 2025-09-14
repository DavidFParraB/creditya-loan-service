package co.credit.app.sqsreceiver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapter.sqs")
public record SQSReceiverProperties(
     String region,
     String queueUrl,
     String endpoint){
}
