package me.juliarn.smartmirror.backend.impl.services.auth;

import io.micronaut.context.BeanContext;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.security.oauth2.endpoint.token.request.TokenEndpointClient;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceAuthRepository;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceTokenProvider;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultServiceTokenProvider implements ServiceTokenProvider {

  private final ServiceAuthRepository serviceAuthRepository;
  private final TokenEndpointClient tokenEndpointClient;
  private final BeanContext beanContext;
  private final Map<TokenKey, TokenResponse> tokenResponses;

  @Inject
  public DefaultServiceTokenProvider(
      ServiceAuthRepository serviceAuthRepository,
      TokenEndpointClient tokenEndpointClient,
      BeanContext beanContext) {
    this.serviceAuthRepository = serviceAuthRepository;
    this.tokenEndpointClient = tokenEndpointClient;
    this.beanContext = beanContext;
    this.tokenResponses = new HashMap<>();
  }

  @Override
  @NonNull
  public Mono<String> getToken(@NonNull String serviceName, @NonNull Account account) {
    TokenKey tokenKey =  new TokenKey(serviceName, account.accountId());
    TokenResponse tokenResponse = this.tokenResponses.get(tokenKey);

    if (tokenResponse != null && tokenResponse.getExpiresInDate().isPresent()) {
      if (tokenResponse.getExpiresInDate().get().getTime() > System.currentTimeMillis()) {
        return Mono.just(tokenResponse.getAccessToken());
      }
    }

    return this.serviceAuthRepository.findByIdAccountAndIdServiceName(account, serviceName)
        .flatMap(serviceAuth -> {
          OauthClientConfiguration oauthClientConfiguration = this.beanContext.getBean(
              OauthClientConfiguration.class, Qualifiers.byName(serviceName));

          return Mono.from(this.tokenEndpointClient.sendRequest(new OauthRefreshTokenRequestContext(
              serviceAuth.refreshToken(),
              oauthClientConfiguration.getTokenEndpoint(),
              oauthClientConfiguration)));
        })
        .map(freshTokenResponse -> {
          this.tokenResponses.put(tokenKey, freshTokenResponse);
          return freshTokenResponse.getAccessToken();
        });
  }

  private record TokenKey(String serviceName, UUID accountId) {

  }
}
