package me.juliarn.smartmirror.backend.impl.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.ServiceAuthRepository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Controller("api/services/auth")
@Secured(Roles.ACCOUNT)
public class ServiceAuthController {

  private final ServiceAuthRepository serviceAuthRepository;

  @Inject
  public ServiceAuthController(ServiceAuthRepository serviceAuthRepository) {
    this.serviceAuthRepository = serviceAuthRepository;
  }

  @Get("{serviceName}")
  Mono<?> hasServiceAuth(@NonNull Authentication authentication, @PathVariable String serviceName) {
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return this.serviceAuthRepository.findByIdAccountAndIdServiceName(account, serviceName)
        .map(serviceAuth -> true)
        .defaultIfEmpty(false)
        .map(hasAuth -> Map.of("hasAuth", hasAuth));
  }

  @Get("{serviceName}/revoke")
  void revokeServiceAuth(@NonNull Authentication authentication, @PathVariable String serviceName) {
    UUID accountId = UUID.fromString(authentication.getName());
    this.serviceAuthRepository.deleteByIdAccountAndIdServiceName(new Account(accountId),
        serviceName);
  }
}
