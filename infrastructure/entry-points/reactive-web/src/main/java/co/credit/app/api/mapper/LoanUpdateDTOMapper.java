package co.credit.app.api.mapper;

import co.credit.app.api.dto.LoanUpdateDTO;
import co.credit.app.model.loan.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanUpdateDTOMapper {

  //@Mapping(source = "statusId", target = "statusId")
  Loan toModel(LoanUpdateDTO loanUpdateDTO);

}
