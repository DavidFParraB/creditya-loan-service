package co.credit.app.api.mapper;

import co.credit.app.api.dto.LoanDTO;
import co.credit.app.api.dto.LoanReportDTO;
import co.credit.app.model.loan.Loan;
import co.credit.app.model.loanreport.LoanReport;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanReportDTOMapper {
    LoanReportDTO toResponse(LoanReport loanReport);

    List<LoanReportDTO> toResponseList (List<LoanReport> loans);
}
