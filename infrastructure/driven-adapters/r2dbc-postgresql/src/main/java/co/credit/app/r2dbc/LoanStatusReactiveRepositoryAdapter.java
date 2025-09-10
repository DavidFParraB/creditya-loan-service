package co.credit.app.r2dbc;

import co.credit.app.model.loanstatus.LoanStatus;
import co.credit.app.model.loanstatus.gateways.LoanStatusRepository;
import co.credit.app.r2dbc.entity.LoanStatusEntity;
import co.credit.app.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Log4j2
public class LoanStatusReactiveRepositoryAdapter
    extends ReactiveAdapterOperations<LoanStatus, LoanStatusEntity, Long, LoanStatusReactiveRepository>
    implements LoanStatusRepository {
  public LoanStatusReactiveRepositoryAdapter(LoanStatusReactiveRepository repository, ObjectMapper mapper) {
    /**
     * Could be use mapper.mapBuilder if your domain model implement builder pattern
     * super(repository, mapper, d ->
     * mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build()); Or using
     * mapper.map with
     * the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, LoanStatus.class));
  }

  @Override
  public Mono<LoanStatus> getLoanStatusById(Long loanStatusId) {
    log.info("Validating loan status: {}", loanStatusId);
    return repository.findById(loanStatusId)
        .doOnSubscribe(l -> log.info("Fetching loan status with id: {}", loanStatusId))
        .doOnNext(loan -> log.info("Found loan with ID: {}", loan.getName()))
        .map(this::toEntity);
  }

  @Override
  public Mono<LoanStatus> getLoanByName(String name) {
    log.info("Fetching loan status with name: {}", name);
    return repository.findByName(name)
        .doOnSubscribe(l -> log.info("Fetching loan status with name: {}", name))
        .doOnNext(loan -> log.info("Found loan with name: {}", loan.getName()))
        .map(this::toEntity);
  }
}
