package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@R2dbcRepository
public interface AccountRepository extends ReactiveStreamsCrudRepository<Account, UUID> {

  @Override
  @NonNull
  Mono<Account> save(@NonNull Account account);

  @Override
  @NonNull
  Mono<Account> findById(@NonNull UUID id);

  @NonNull
  Mono<Account> findByAccountName(@NotBlank String accountName);
}
