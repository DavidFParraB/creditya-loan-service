package co.credit.app.model.availabledebt;
import co.credit.app.model.loan.Loan;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AvailableDebt {
  private Double amount;
  private Double rate;
  private Integer term;
}
