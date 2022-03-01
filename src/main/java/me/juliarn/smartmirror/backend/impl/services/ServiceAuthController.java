package me.juliarn.smartmirror.backend.impl.services;

import io.micronaut.context.BeanContext;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.oauth2.client.OauthClient;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.Map;
import java.util.UUID;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.ServiceAuthRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("api/services")
@Secured(SecurityRule.IS_ANONYMOUS) // TODO: 01.03.2022 remove
public class ServiceAuthController {

  private final ServiceAuthRepository serviceAuthRepository;
  private final BeanContext beanContext;

  @Inject
  public ServiceAuthController(
      ServiceAuthRepository serviceAuthRepository,
      BeanContext beanContext) {
    this.serviceAuthRepository = serviceAuthRepository;
    this.beanContext = beanContext;
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

  @Get("auth/{serviceName}/login")
  Publisher<?> serviceLogin(@NonNull HttpRequest<?> request, @PathVariable String serviceName) {
    return this.beanContext.findBean(OauthClient.class, Qualifiers.byName(serviceName))
        .map(oauthClient -> oauthClient.authorizationRedirect(request))
        .orElse(Mono.just(HttpResponse.badRequest(new JsonError("Invalid service name supplied"))));
  }

  @Get("auth/{serviceName}/callback")
  Publisher<?> serviceCallback(
      @NonNull HttpRequest<Map<String, Object>> request,
      @PathVariable String serviceName) {
    return this.beanContext.findBean(OauthClient.class, Qualifiers.byName(serviceName))
        .map(oauthClient -> Flux.from(oauthClient.onCallback(request))
            .flatMap(response -> {
              if (response.isAuthenticated() && response.getAuthentication().isPresent()) {
                return Mono.just(HttpResponse.ok(response.getAuthentication().get()));
              }
              return Mono.just(HttpResponse.ok());
            }))
        .orElse(Flux.just(HttpResponse.badRequest(new JsonError("Invalid service name supplied"))));
  }
}
