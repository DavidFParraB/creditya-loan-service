package co.credit.app.usecase.loanreport;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loanfilter.LoanFilter;
import co.credit.app.model.loanreport.LoanReport;
import co.credit.app.model.user.User;
import co.credit.app.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class LoanReportUseCase {

  private final UserRepository userRepository;
  private final LoanRepository loanRepository;

  public Flux<LoanReport> generateLoanReport(LoanFilter filter) {
    return loanRepository.getAllLoansWithPagination(filter)
        .flatMap(loan -> userRepository.findUserByDocument(loan.getDocument())
            .map(user -> buildLoanReport(loan, user)));
  }

  private LoanReport buildLoanReport(Loan loan, User user) {
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
