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
    @NotBlank(message = "El documento es obligatorio")
    private String document;
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotNull(message = "El amount es obligatorio")
    @Min(value = 1, message = "El monto mínimo es $1")
    @Max(value = 1000000, message = "El monto máximo es $1,000,000")
    private Double amount;
    @NotNull(message = "El plazo es obligatorio")
    @Min(value = 1, message = "El plazo mínimo es 1")
    @Max(value = 1000000, message = "El plazo máximo es 36")
    private Integer term;
    @JsonProperty("status_id")
    @JsonIgnore
    private Long statusId;
    @JsonProperty("loan_type_id")
    private Long loanTypeId;

}
