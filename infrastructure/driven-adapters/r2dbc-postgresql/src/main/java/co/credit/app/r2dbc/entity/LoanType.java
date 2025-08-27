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
@Table(name = "loan_type")
public class LoanType {
  @Id
  private Long id;
  private String name;
  @JsonProperty("interest_rate")
  private Double interestRate;
  @JsonProperty("minimum_amount")
  private Double minimumAmount;
  @JsonProperty("maximum_amount")
  private Double maximumAmount;
  @JsonProperty("is_automatic")
  private Boolean isAutomatic;
  

}
