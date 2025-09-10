package co.credit.app.usecase.loan;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loanfilter.LoanFilter;
import co.credit.app.model.loanreport.LoanReport;
import co.credit.app.model.loanreport.gateways.LoanReportRepository;
import co.credit.app.model.loanstatus.gateways.LoanStatusRepository;
import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import co.credit.app.model.mail.Mail;
import co.credit.app.model.mail.gateways.MailRepository;
import co.credit.app.model.user.gateways.UserRepository;
import co.credit.app.usecase.utils.Constants;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {

  private final LoanRepository loanRepository;
  private final LoanTypeRepository loanTypeRepository;
  private final UserRepository userRepository;
  private final LoanReportRepository loanReportService;
  private final LoanStatusRepository loanStatusRepository;
  private final MailRepository mailRepository;

  public Mono<Void> saveLoan(Loan loan) {

    return loanTypeRepository.isValidLoanType(loan.getLoanTypeId())
        .flatMap(exist -> {
          if (Boolean.TRUE.equals(exist)) {

            return userRepository.findUserByDocument(loan.getDocument())
                .flatMap(user -> {
                  loan.setStatusId(Constants.LOAN_STATUS_PENDING);
                  return loanRepository.saveLoan(loan);
                }).then()
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("Invalid User.")));

          } else {
            return Mono.error(new IllegalArgumentException("Invalid loan type."));
          }
        });
  }

  public Mono<Void> updateLoan(Long id, Loan loan) {

    return loanStatusRepository.isValidLoanStatus(loan.getStatusId())
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid loan status.")))
        .flatMap(loanStatus -> {
          return loanRepository.getLoanById(id)
              .switchIfEmpty(Mono.error(new IllegalArgumentException("Loan not found.")))
              .flatMap(dbLoan -> {
                dbLoan.setStatusId(loan.getStatusId());
                return loanRepository.saveLoan(dbLoan).then(mailRepository.sendMail(
                    Mail.builder().recipientEmail(dbLoan.getEmail())
                        .subject("Loan application number " + dbLoan.getId() +" updated.")
                        .body("Your loan has been: " + loanStatus.getName() + ".")
                        .build())).then();
              }).then();
        });
  }

  public Flux<Loan> getAllLoans() {
    return loanRepository.getAllLoans();
  }

  public Flux<Loan> getAllLoansWithPagination(LoanFilter filter) {
    return loanRepository.getAllLoansWithPagination(filter)
        .onErrorResume(e -> Flux.error(
            new IllegalArgumentException("Error fetching loans." + e.getMessage())));
  }

  public Flux<LoanReport> generateLoanReport(LoanFilter filter) {
    return loanRepository.getAllLoansWithPagination(filter)
        .flatMap(loan -> userRepository.findUserByDocument(loan.getDocument())
            .map(user -> loanReportService.buildLoanReport(loan, user)));
  }

}
