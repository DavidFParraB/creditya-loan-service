package co.credit.app.r2dbc;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import co.credit.app.model.loantype.LoanType;
import co.credit.app.model.loantype.gateways.LoanTypeRepository;
import co.credit.app.r2dbc.entity.LoanTypeEntity;
import co.credit.app.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Repository
@Log4j2
public class LoanTypeReactiveRepositoryAdapter
    extends ReactiveAdapterOperations<LoanType, LoanTypeEntity, Long, LoanTypeReactiveRepository>
    implements LoanTypeRepository {
  public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {
    /**
     * Could be use mapper.mapBuilder if your domain model implement builder pattern
     * super(repository, mapper, d ->
     * mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build()); Or using
     * mapper.map with
     * the class of the object model
     */
    super(repository, mapper, d -> mapper.map(d, LoanType.class));
  }

  @Override
  public Mono<LoanType> findById(Long loanTypeId) {
    log.info("Fetching loan type with id: {}", loanTypeId);
    return repository.findById(loanTypeId)
        .doOnSubscribe(s -> log.info("Fetching loan type with id: {}", loanTypeId))
        .map(this::toEntity)
        .doOnNext(
            loanType -> log.info("Found loan type with ID: {} - automatic: {}", loanType.getName(),
                loanType.getIsAutomatic()));
  }
}
