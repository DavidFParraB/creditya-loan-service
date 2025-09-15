package co.credit.app.usecase.debtcapacity;

import co.credit.app.model.availabledebt.AvailableDebt;
import co.credit.app.model.debtcapacity.DebtCapacity;
import co.credit.app.model.debtcapacity.gateways.DebtCapacityRepository;
import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.model.loantype.LoanType;
import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import co.credit.app.model.user.User;
import co.credit.app.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DebtCapacityUseCase {

  private static final Long LOAN_STATUS_APPROVED = 2L;

  private final DebtCapacityRepository debtCapacityRepository;
  private final LoanRepository loanRepository;
  private final LoanTypeRepository loanTypeRepository;

  public Mono<Void> validateDebtCapacity(Loan newLoan, LoanType loanTypeIn, User userIn) {

    return loanRepository.getLoansByStatusAndDocument(LOAN_STATUS_APPROVED, userIn.getDocument())
        .flatMap(loans -> {
          Mono<LoanType> loanTypeMono = loanTypeRepository.findById(loans.getLoanTypeId());
          return Mono.zip(Mono.just(loans), loanTypeMono).map(t -> {
            Loan loan = t.getT1();
            LoanType loanType = t.getT2();
            return AvailableDebt.builder().rate(loanType.getInterestRate()).amount(loan.getAmount())
                .term(loan.getTerm()).build();
          });
        }).collectList().flatMap(debtCapacityList -> {
          DebtCapacity debtCapacity = DebtCapacity.builder().approvedLoans(debtCapacityList)
              .id(newLoan.getId())
              .salary(userIn.getSalary()).loan(
                  AvailableDebt.builder().amount(newLoan.getAmount())
                      .rate(loanTypeIn.getInterestRate()).term(newLoan.getTerm()).build()).build();
          return debtCapacityRepository.sendDebtCapacity(debtCapacity).then();
        });
  }

}
