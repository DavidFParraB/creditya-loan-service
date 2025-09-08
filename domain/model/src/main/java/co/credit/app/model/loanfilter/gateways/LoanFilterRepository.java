package co.credit.app.model.loanfilter.gateways;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loanreport.LoanReport;
import co.credit.app.model.user.User;

public interface LoanFilterRepository {

  LoanReport buildLoanReport(Loan loan, User user);

}
