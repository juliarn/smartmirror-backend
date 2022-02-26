package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface AccountRepository extends ReactiveStreamsCrudRepository<Account, UUID> {

  @Override
  @NonNull
  @SingleResult
  Mono<Account> save(@Valid @NotNull @NonNull Account entity);

  @Override
  @SingleResult
  @NonNull
  Mono<Long> delete(@NonNull @NotNull Account account);

  @Override
  @NonNull
  Mono<Account> findById(@NonNull UUID id);

  @NonNull
  Mono<Account> findByAccountName(@NotBlank String accountName);
}
