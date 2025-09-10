package co.credit.app.api.mapper;

import co.credit.app.api.dto.LoanUpdateDTO;
import java.util.List;

import org.mapstruct.Mapper;

import co.credit.app.api.dto.LoanDTO;
import co.credit.app.model.loan.Loan;

@Mapper(componentModel = "spring")
public interface LoanDTOMapper {
    LoanDTO toResponse(Loan loan);

    List<LoanDTO> toResponseList (List<Loan> loans);

    Loan toModel(LoanDTO loanDTO);
}
