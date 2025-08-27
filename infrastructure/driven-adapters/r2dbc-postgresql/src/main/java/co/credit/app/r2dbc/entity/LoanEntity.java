package co.credit.app.r2dbc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class LoanEntity {
	@Id
    private Long id;
    private String email;
    private Double amount;
    private Integer term;
    @JsonProperty("status_id")
    private Integer statusId;
    @JsonProperty("loan_type_id")
    private Integer loandTypeId;
}
