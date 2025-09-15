package co.credit.app.usecase.mail;

import co.credit.app.model.loan.Loan;
import co.credit.app.model.loantype.LoanType;
import co.credit.app.model.mail.Mail;
import co.credit.app.model.mail.gateways.MailRepository;
import co.credit.app.usecase.utils.Constants;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MailUseCase {

  private final MailRepository mailRepository;

  public Mono<Void> sendMail(Loan loan, LoanType loanType, String status) {
    String body = "Your loan has been: " + status + ".";
    if (Constants.STATUS_APPROVED.equalsIgnoreCase(status)) {
      String additionalDetails =
          " \n \nPAYMENT PLAN: \n" + paymentPlan(loan, loanType).stream().map(String::trim)
              .collect(Collectors.joining("\n"));
      body += additionalDetails;
    }

    Mail mail = Mail.builder().recipientEmail(loan.getEmail())
        .subject("Loan application number " + loan.getId() + " updated.")
        .body(body).build();

    return mailRepository.sendMail(mail).then();
  }

  private List<String> paymentPlan(Loan loan, LoanType loanType) {
    List<String> row = new ArrayList<>();
    double saldo = loan.getAmount();
    DecimalFormat df = new DecimalFormat("#.##");
    row.add("Mes | Cuota | Interes | Abono a Capital | Saldo Restante");
    double cuotaMensual = installmentAmount(loan.getAmount(), (loanType.getInterestRate() / (12*100)),
        loan.getTerm());

    for (int mes = 1; mes <= loan.getTerm(); mes++) {
      double interes = saldo * (loanType.getInterestRate() / (12*100)) ;
      double abonoCapital = cuotaMensual - interes;
      saldo -= abonoCapital;

      if (mes == loan.getTerm() && saldo > 0) {
        abonoCapital += saldo;
        saldo = 0;
      }

      String detalle = String.format("%d | %s | %s | %s | %s", mes, df.format(cuotaMensual),
          df.format(interes), df.format(abonoCapital), df.format(saldo));
      row.add(detalle);
    }
    return row;
  }

  private double installmentAmount(double P, double i, int n) {
    if (i == 0) {
      return P / n;
    }
    return (P * i ) / (1 - Math.pow(1 + i , -n));
  }
}
