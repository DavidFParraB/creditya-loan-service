package co.credit.app.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import co.credit.app.r2dbc.entity.LoanEntity;
import reactor.core.publisher.Flux;

@Repository
public interface MyReactiveRepository
    extends ReactiveCrudRepository<LoanEntity, Long>, ReactiveQueryByExampleExecutor<LoanEntity> {

  @Query("SELECT * FROM loan LIMIT :limit OFFSET :offset")
  Flux<LoanEntity> findAllWithPagination(@Param("limit") int limit, @Param("offset") int offset);
}
