package co.credit.app.model.debtcapacity;
import co.credit.app.model.availabledebt.AvailableDebt;
import java.util.List;
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
public class DebtCapacity {
  private Long id;
  private Double salary;
  private AvailableDebt loan;
  private List<AvailableDebt> approvedLoans;
}
