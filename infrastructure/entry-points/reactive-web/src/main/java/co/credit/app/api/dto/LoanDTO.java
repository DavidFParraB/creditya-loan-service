package co.credit.app.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoanDTO {
    @JsonIgnore
    private Long id;
    @NotBlank(message = "El documento es obligatorio")
    private String document;
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @NotBlank(message = "El amount es obligatorio")
    @NotNull
    @Min(value = 1, message = "El monto mínimo es $1")
    @Max(value = 1000000, message = "El monto máximo es $1,000,000")
    private Double amount;
    @NotBlank(message = "La tasa del credito es obligatoria")
    @NotNull
    private Integer term;
    @JsonProperty("status_id")
    @JsonIgnore
    private Long statusId;
    @JsonProperty("loan_type_id")
    private Long loandTypeId;

}
