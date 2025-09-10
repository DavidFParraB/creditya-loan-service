package co.credit.app.r2dbc;

import co.credit.app.r2dbc.entity.LoanStatusEntity;
import co.credit.app.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStatusReactiveRepository extends ReactiveCrudRepository<LoanStatusEntity, Long>,
        ReactiveQueryByExampleExecutor<LoanStatusEntity> {

}
