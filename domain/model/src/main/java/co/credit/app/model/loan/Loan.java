package co.credit.app.model.loan;

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
public class Loan {

	private Long id;
	private String document;
	private String email;
	private Double amount;
	private Integer term;
	private Long statusId;
	private Long loandTypeId;
}
