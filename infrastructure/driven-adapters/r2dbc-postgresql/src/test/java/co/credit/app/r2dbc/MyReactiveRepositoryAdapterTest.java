package co.credit.app.r2dbc;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import co.credit.app.model.loan.Loan;
import co.credit.app.r2dbc.entity.LoanEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {

  @InjectMocks
  MyReactiveRepositoryAdapter repositoryAdapter;

  @Mock
  MyReactiveRepository repository;

  @Mock
  ObjectMapper mapper;

  @Test
  @DisplayName("Findy Loan by id")
  void findLoanByIdTest() {
    LoanEntity loanEntity = gEntity();
    Loan loanObj = getLoan();
    when(repository.findById(1L)).thenReturn(Mono.just(loanEntity));
    when(mapper.map(loanEntity, Loan.class)).thenReturn(loanObj);

    Mono<Loan> result = repositoryAdapter.findById(1L);

    StepVerifier.create(result)
        .expectNextMatches(value -> value.equals(loanObj))
        .verifyComplete();
  }

  @Test
  @DisplayName("Find all loans")
  void findAllLoansTest() {
    LoanEntity loanEntity = gEntity();
    Loan loanObj = getLoan();
    when(repository.findAll()).thenReturn(Flux.just(loanEntity));
    when(mapper.map(loanEntity, Loan.class)).thenReturn(loanObj);

    Flux<Loan> result = repositoryAdapter.getAllLoans();

    StepVerifier.create(result)
        .expectNext(loanObj)
        .verifyComplete();
  }

  @Test
  @DisplayName("Save loan")
  void saveLoanTest() {
    LoanEntity loanEntity = gEntity();
    Loan loanObj = getLoan();

    when(mapper.map(loanObj, LoanEntity.class)).thenReturn(loanEntity);
    when(repository.save(loanEntity)).thenReturn(Mono.just(loanEntity));
    when(mapper.map(loanEntity, Loan.class)).thenReturn(loanObj);


    Mono<Loan> result = repositoryAdapter.saveLoan(loanObj);

    StepVerifier.create(result)
        .expectNext(loanObj)
        .verifyComplete();
  }

  private LoanEntity gEntity() {
    LoanEntity entity = new LoanEntity();
    entity.setId(1L);
    return entity;
  }

  private Loan getLoan() {
    Loan loan = new Loan();
    loan.setId(1L);
    return loan;
  }
}
