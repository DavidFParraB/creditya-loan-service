package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoanUpdateDTO {
  @NotNull(message = "The loan status is mandatory:[2,3]")
  @JsonProperty("status")
  @Pattern(regexp = "APPROVED|REJECTED", message = "The loan status must be APPROVED or REJECTED")
  private String statusName;
}
