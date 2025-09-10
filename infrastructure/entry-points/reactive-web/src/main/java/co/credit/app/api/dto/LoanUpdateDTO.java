package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanUpdateDTO {
  @NotNull(message = "The loan status is mandatory:[2,3]")
  @JsonProperty("status_id")
  private Long statusId;
}
