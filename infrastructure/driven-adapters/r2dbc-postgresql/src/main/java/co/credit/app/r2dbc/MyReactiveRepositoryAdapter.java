package co.credit.app.r2dbc;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import co.credit.app.model.loan.Loan;
import co.credit.app.model.loan.gateways.LoanRepository;
import co.credit.app.r2dbc.entity.LoanEntity;
import co.credit.app.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter
    extends ReactiveAdapterOperations<Loan, LoanEntity, Long, MyReactiveRepository>
    implements LoanRepository {
  public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
    /**
     * Could be use mapper.mapBuilder if your domain model implement builder pattern
     * super(repository, mapper, d ->
     * mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build()); Or using mapper.map with
     * the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, Loan.class));
  }

  @Override
  public Mono<Void> saveLoan(Loan loan) {
    return repository.save(toData(loan)).then();
  }

  @Override
  public Flux<Loan> getAllLoans() {
    return repository.findAll().map(this::toEntity);
  }

}
