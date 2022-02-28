package me.juliarn.smartmirror.backend.impl.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import java.util.Map;
import java.util.UUID;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.ServiceAuthRepository;
import reactor.core.publisher.Mono;

@Controller("api/services")
public class ServiceAuthController {

  private final ServiceAuthRepository serviceAuthRepository;

  @Inject
  public ServiceAuthController(
      ServiceAuthRepository serviceAuthRepository) {
    this.serviceAuthRepository = serviceAuthRepository;
  }

  @Get("/auth/{serviceName}")
  Mono<?> hasServiceAuth(@NonNull Authentication authentication, @PathVariable String serviceName) {
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return this.serviceAuthRepository.findByIdAccountAndIdServiceName(account, serviceName)
        .map(serviceAuth -> true)
        .defaultIfEmpty(false)
        .map(hasAuth -> Map.of("hasAuth", hasAuth));
  }
}
