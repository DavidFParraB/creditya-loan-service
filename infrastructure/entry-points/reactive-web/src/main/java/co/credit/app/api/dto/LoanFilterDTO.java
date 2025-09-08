package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanFilterDTO {
  @NotNull(message = "Status cannot be null")
  @Min(value = 1, message = "Status must be a positive number")
  @Digits(integer = 10, fraction = 0, message = "Status ID cannot have decimal places")
  @JsonProperty("status")
  private Integer statusId;

  @Min(value = 0, message = "Page must be a positive number")
  @Digits(integer = 10, fraction = 0, message = "page cannot have decimal places")
  private Integer page;

  @Min(value = 1, message = "Size must be a positive number")
  @Digits(integer = 10, fraction = 0, message = "Status ID cannot have decimal places")
  private Integer size;
}
