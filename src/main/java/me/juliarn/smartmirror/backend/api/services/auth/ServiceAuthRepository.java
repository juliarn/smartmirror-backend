package me.juliarn.smartmirror.backend.api.services.auth;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import me.juliarn.smartmirror.backend.api.account.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository which holds all refresh token for OAuth2 services.
 */
@R2dbcRepository(dialect = Dialect.MYSQL)
public interface ServiceAuthRepository extends ReactorCrudRepository<ServiceAuth, ServiceAuthId> {

  /**
   * Finds all tokens related to a certain account.
   *
   * @param account The account which the tokens belong to
   * @return A publisher which emits all existing tokens of the account
   */
  @Join(value = "account")
  Flux<ServiceAuth> findByIdAccount(@NonNull Account account);

  /**
   * Finds a token of specific service related to a certain account.
   *
   * @param account The account the token belongs to
   * @param serviceName The name of the service the token is for
   * @return A publisher which emits the token or nothing, if no token exists
   */
  @SingleResult
  @Join(value = "account")
  Mono<ServiceAuth> findByIdAccountAndIdServiceName(
      @NonNull Account account,
      @NonNull String serviceName);
}
