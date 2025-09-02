package co.credit.app.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanDTO {
    @JsonIgnore
    private Long id;
    @NotBlank(message = "The document is mandatory")
    private String document;
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "The amount is required")
    @Min(value = 100000, message = "The minimum amount is $100,000")
    @Max(value = 5000000, message = "The maximum amount is $5,000,000")
    private Double amount;
    @NotNull(message = "The loan term is mandatory")
    @Min(value = 1, message = "The minimum term is 1 month")
    @Max(value = 36, message = "The maximum term is 36 months")
    private Integer term;
    @JsonProperty("status_id")
    @JsonIgnore
    private Long statusId;
    @NotNull(message = "The loan type is mandatory")
    @JsonProperty("loan_type_id")
    private Long loanTypeId;

}
