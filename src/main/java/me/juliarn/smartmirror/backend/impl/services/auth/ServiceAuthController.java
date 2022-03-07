package me.juliarn.smartmirror.backend.impl.services.auth;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.Map;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceAuthId;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceAuthRepository;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceTokenProvider;
import reactor.core.publisher.Mono;

@Controller("api/services/auth")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class ServiceAuthController {

  private final ServiceAuthRepository serviceAuthRepository;
  private final ServiceTokenProvider serviceTokenProvider;

  @Inject
  public ServiceAuthController(
      ServiceAuthRepository serviceAuthRepository,
      ServiceTokenProvider serviceTokenProvider) {
    this.serviceAuthRepository = serviceAuthRepository;
    this.serviceTokenProvider = serviceTokenProvider;
  }

  @Get("{serviceName}")
  Mono<?> hasServiceAuth(@NonNull Authentication authentication, @PathVariable String serviceName) {
    Account account = Account.fromAuthentication(authentication);

    return this.serviceAuthRepository.findByIdAccountAndIdServiceName(account, serviceName)
        .map(serviceAuth -> true)
        .defaultIfEmpty(false)
        .map(hasAuth -> Map.of("hasAuth", hasAuth));
  }

  @Get("{serviceName}/revoke")
  Mono<?> revokeServiceAuth(
      @NonNull Authentication authentication,
      @PathVariable String serviceName) {
    Account account = Account.fromAuthentication(authentication);

    this.serviceTokenProvider.removeToken(serviceName, account);
    return this.serviceAuthRepository.deleteById(
        new ServiceAuthId(account, serviceName)).map(id -> HttpResponse.ok());
  }
}
