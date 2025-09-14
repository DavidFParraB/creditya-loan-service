package co.credit.app.sqs.listener.dto;

import lombok.Data;

@Data
public class LoanReceiverDTO {
  private Long loan;
  private String status;
}
