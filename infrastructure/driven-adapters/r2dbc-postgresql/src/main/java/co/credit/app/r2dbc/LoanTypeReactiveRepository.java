package co.credit.app.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import co.credit.app.r2dbc.entity.LoanTypeEntity;

@Repository
public interface LoanTypeReactiveRepository extends ReactiveCrudRepository<LoanTypeEntity, Long>,
        ReactiveQueryByExampleExecutor<LoanTypeEntity> {

}
