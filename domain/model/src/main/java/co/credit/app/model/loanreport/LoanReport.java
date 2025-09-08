package co.credit.app.model.loanreport;
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
public class LoanReport {

  private Long id;
  private Double amount;
  private Integer term;
  private Long statusId;
  private Long loanTypeId;
  private String name;
  private String lastName;
  private String email;
  private String document;
  private Double salary;
}
