package me.juliarn.smartmirror.backend.api.services.auth;

import io.micronaut.core.annotation.NonNull;
import me.juliarn.smartmirror.backend.api.account.Account;
import reactor.core.publisher.Mono;

/**
 * Provides actual api access tokens for OAuth2 services based on the refresh tokens stored in the
 * {@link ServiceAuthRepository}.
 */
public interface ServiceTokenProvider {

  /**
   * Provides an api access token for a specific service, related to a certain account.
   *
   * @param serviceName The name of the service
   * @param account The account the token belongs to
   * @return A publisher emitting the api access token or nothing, if no refresh token present
   */
  @NonNull
  Mono<String> getToken(@NonNull String serviceName, @NonNull Account account);

  /**
   * Removes all refresh and api access tokens of a specific service, related to a certain account.
   *
   * @param serviceName The name of the serivce
   * @param account The account the tokens belong to
   */
  void removeToken(@NonNull String serviceName, @NonNull Account account);
}
