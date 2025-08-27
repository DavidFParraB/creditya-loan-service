package co.credit.app.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import co.credit.app.r2dbc.entity.LoanEntity;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository
    extends ReactiveCrudRepository<LoanEntity, Long>, ReactiveQueryByExampleExecutor<LoanEntity> {

}
