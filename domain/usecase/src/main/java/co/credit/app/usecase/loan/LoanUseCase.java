package co.credit.app.usecase.loan;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loanstatus.gateways.LoanStatusRepository;
import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import co.credit.app.model.user.gateways.UserRepository;
import co.credit.app.usecase.debtcapacity.DebtCapacityUseCase;
import co.credit.app.usecase.loannotification.LoanNotificationUseCase;
import co.credit.app.usecase.utils.Constants;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {

  private final LoanRepository loanRepository;
  private final LoanTypeRepository loanTypeRepository;
  private final UserRepository userRepository;
  private final LoanStatusRepository loanStatusRepository;
  private final LoanNotificationUseCase loanNotificationUseCase;
  private final DebtCapacityUseCase debtCapacityUseCase;

  public Mono<Void> saveLoan(Loan loan) {
    return loanTypeRepository.findById(loan.getLoanTypeId())
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid loan type.")))
        .flatMap(loanType -> {
          return userRepository.findUserByDocument(loan.getDocument())
              .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid User.")))
              .flatMap(user -> {
                loan.setStatusId(Constants.LOAN_STATUS_PENDING);
                return loanRepository.saveLoan(loan).flatMap(savedLoan -> {
                  if (loanType.getIsAutomatic()) {
                    loan.setId(savedLoan.getId());
                    return debtCapacityUseCase.validateDebtCapacity(loan, loanType, user).then();
                  }
                  return Mono.empty();
                }).then();
              });
        });
  }

  public Mono<Void> updateLoan(Long id, String statusName) {

    return loanStatusRepository.getLoanByName(statusName)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid loan status.")))
        .flatMap(loanStatus -> {
          return loanRepository.getLoanById(id)
              .switchIfEmpty(Mono.error(new IllegalArgumentException("Loan not found.")))
              .filter(dbLoan -> dbLoan.getStatusId() == 1)
              .switchIfEmpty(Mono.error(new IllegalArgumentException("loan already validated.")))
              .flatMap(dbLoan -> {
                dbLoan.setStatusId(loanStatus.getId());
                return loanRepository.saveLoan(dbLoan)
                    .flatMap(updatedLoan -> {
                      return loanTypeRepository.findById(updatedLoan.getLoanTypeId())
                          .flatMap(
                              loanType -> loanNotificationUseCase.sendNotification(updatedLoan, loanType, statusName));
                    }).then();
              }).then();
        });
  }

  public Flux<Loan> getAllLoans() {
    return loanRepository.getAllLoans();
  }
}
