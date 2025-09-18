package co.credit.app.model.loannotification;
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
public class LoanNotification {
  private Long loan;
  private String email;
  private String status;
  private Double amount;
  private Integer term;
  private Double rate;
}
