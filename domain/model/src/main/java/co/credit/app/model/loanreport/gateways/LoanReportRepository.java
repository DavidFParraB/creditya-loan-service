package co.credit.app.model.loanreport.gateways;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loanreport.LoanReport;
import co.credit.app.model.user.User;

public interface LoanReportRepository {
  LoanReport buildLoanReport(Loan loan, User user);
}
