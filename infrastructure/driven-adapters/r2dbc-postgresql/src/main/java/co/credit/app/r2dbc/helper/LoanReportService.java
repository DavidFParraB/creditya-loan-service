package co.credit.app.r2dbc.helper;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loanreport.LoanReport;
import co.credit.app.model.loanreport.gateways.LoanReportRepository;
import co.credit.app.model.user.User;
import org.springframework.stereotype.Service;

@Service
public class LoanReportService implements LoanReportRepository {

  @Override
  public LoanReport buildLoanReport(Loan loan, User user) {
    return LoanReport.builder()
        .id(loan.getId())
        .amount(loan.getAmount())
        .term(loan.getTerm())
        .statusId(loan.getStatusId())
        .loanTypeId(loan.getLoanTypeId())
        .name(user.getName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .document(user.getDocument())
        .salary(user.getSalary())
        .build();
  }
}