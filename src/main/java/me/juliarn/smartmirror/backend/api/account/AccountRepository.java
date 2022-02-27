package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface AccountRepository extends ReactorCrudRepository<Account, UUID> {

  @NonNull
  @SingleResult
  Mono<Account> findByAccountNameIlike(@NotBlank String accountName);
}
