package co.credit.app.r2dbc;

import static org.mockito.Mockito.*;

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
    // TODO: change four you own tests

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        LoanEntity loanEntity = gEntity();
        Loan loanObj = getLoan();
        when(repository.findById(1L)).thenReturn(Mono.just(loanEntity));
        when(mapper.map(loanEntity, Loan.class)).thenReturn(loanObj);

        Mono<Loan> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(loanObj))
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

    @Test
    void mustFindAllValues() {
        LoanEntity loanEntity = gEntity();
        Loan loanObj = getLoan();
        when(repository.findAll()).thenReturn(Flux.just(loanEntity));
        when(mapper.map(loanEntity, Loan.class)).thenReturn(loanObj);

        Flux<Loan> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                // .expectNext(value -> value.equals(loanObj))
                .expectNext(loanObj)
                .verifyComplete();
    }

    /*
     * @Test
     * void mustFindByExample() {
     * when(repository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
     * when(mapper.map("test", Object.class)).thenReturn("test");
     * 
     * Flux<Object> result = repositoryAdapter.findByExample("test");
     * 
     * StepVerifier.create(result)
     * .expectNextMatches(value -> value.equals("test"))
     * .verifyComplete();
     * }
     * 
     * @Test
     * void mustSaveValue() {
     * when(repository.save("test")).thenReturn(Mono.just("test"));
     * when(mapper.map("test", Object.class)).thenReturn("test");
     * 
     * Mono<Object> result = repositoryAdapter.save("test");
     * 
     * StepVerifier.create(result)
     * .expectNextMatches(value -> value.equals("test"))
     * .verifyComplete();
     * }
     */
}
