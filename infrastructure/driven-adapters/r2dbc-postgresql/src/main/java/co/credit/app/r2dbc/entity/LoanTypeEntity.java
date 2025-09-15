package co.credit.app.r2dbc.entity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
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
public class LoanTypeEntity {
  @Id
  private Long id;
  private String name;
  @Column(name = "interest_rate", nullable = false)
  private Double interestRate;
  @Column(name = "minimum_amount", nullable = false)
  private Double minimumAmount;
  @Column(name = "maximum_amount", nullable = false)
  private Double maximumAmount;
  @Column(name = "is_automatic", nullable = false)
  private Boolean isAutomatic;

}
