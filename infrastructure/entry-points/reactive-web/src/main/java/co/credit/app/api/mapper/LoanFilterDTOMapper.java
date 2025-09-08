package co.credit.app.api.mapper;

import co.credit.app.api.dto.LoanDTO;
import co.credit.app.api.dto.LoanFilterDTO;
import co.credit.app.model.loan.Loan;
import co.credit.app.model.loanfilter.LoanFilter;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanFilterDTOMapper {

  LoanFilterDTO toResponse(LoanFilter loanFilter);

  List<LoanFilterDTO> toResponseList(List<LoanFilter> loans);

  LoanFilter toModel(LoanFilterDTO userDTO);
}
