package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

/**
 * Repository which holds all user accounts and allows their management.
 */
@R2dbcRepository(dialect = Dialect.MYSQL)
public interface AccountRepository extends ReactorCrudRepository<Account, Long> {

  /**
   * Finds an account based on the account name. The comparision is case-insensitive.
   *
   * @param accountName The account name to search for
   * @return A publisher which emits the account or nothing, if no account found
   */
  @NonNull
  @SingleResult
  Mono<Account> findByAccountNameIlike(@NotBlank String accountName);
}
