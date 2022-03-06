package me.juliarn.smartmirror.backend.api.services.auth;

import io.micronaut.core.annotation.NonNull;
import me.juliarn.smartmirror.backend.api.account.Account;
import reactor.core.publisher.Mono;

public interface ServiceTokenProvider {

  @NonNull
  Mono<String> getToken(@NonNull String serviceName, @NonNull Account account);
}
