package co.credit.app.usecase.loanreport;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loanfilter.LoanFilter;
import co.credit.app.model.loanreport.LoanReport;
import co.credit.app.model.loanreport.gateways.LoanReportRepository;
import co.credit.app.model.user.User;
import co.credit.app.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class LoanReportUseCase {

  private final UserRepository userRepository;
  private final LoanRepository loanRepository;
  private final LoanReportRepository loanReportRepository;

  public Flux<LoanReport> generateLoanReport(LoanFilter filter) {
    return loanRepository.getAllLoansWithPagination(filter)
        .flatMap(loan -> userRepository.findUserByDocument(loan.getDocument())
            .map(user -> loanReportRepository.buildLoanReport(loan, user)));
  }


}
