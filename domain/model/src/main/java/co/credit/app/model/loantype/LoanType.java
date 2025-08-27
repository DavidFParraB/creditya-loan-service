package co.credit.app.model.loantype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    private Integer id;
    private String name;
    private Double interestRate;
    private Double minimumAmount;
    private Double maximumAmount;
    private Boolean isAutomaticVaalidation;
}
