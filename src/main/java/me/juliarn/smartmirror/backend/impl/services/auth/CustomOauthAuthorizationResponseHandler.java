package me.juliarn.smartmirror.backend.impl.services.auth;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.security.oauth2.endpoint.SecureEndpoint;
import io.micronaut.security.oauth2.endpoint.authorization.response.AuthorizationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.response.DefaultOauthAuthorizationResponseHandler;
import io.micronaut.security.oauth2.endpoint.authorization.response.OauthAuthorizationResponseHandler;
import io.micronaut.security.oauth2.endpoint.authorization.state.InvalidStateException;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.authorization.state.validation.StateValidator;
import io.micronaut.security.oauth2.endpoint.token.request.TokenEndpointClient;
import io.micronaut.security.oauth2.endpoint.token.request.context.OauthCodeTokenRequestContext;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceAuth;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceAuthId;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceAuthRepository;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@Replaces(DefaultOauthAuthorizationResponseHandler.class)
public class CustomOauthAuthorizationResponseHandler implements OauthAuthorizationResponseHandler {

  private static final Logger LOG = LoggerFactory.getLogger(
      CustomOauthAuthorizationResponseHandler.class);

  private final TokenEndpointClient tokenEndpointClient;
  private final ServiceAuthRepository serviceAuthRepository;

  @Nullable
  private final StateValidator stateValidator;

  @Inject
  CustomOauthAuthorizationResponseHandler(
      TokenEndpointClient tokenEndpointClient,
      ServiceAuthRepository serviceAuthRepository,
      @Nullable StateValidator stateValidator) {
    this.tokenEndpointClient = tokenEndpointClient;
    this.serviceAuthRepository = serviceAuthRepository;
    this.stateValidator = stateValidator;
  }

  @Override
  public Publisher<AuthenticationResponse> handle(
      AuthorizationResponse authorizationResponse,
      OauthClientConfiguration clientConfiguration,
      OauthAuthenticationMapper authenticationMapper,
      SecureEndpoint tokenEndpoint) {

    State state;
    if (this.stateValidator != null) {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Validating state found in the authorization response from provider [{}]",
            clientConfiguration.getName());
      }
      state = authorizationResponse.getState();
      try {
        this.stateValidator.validate(authorizationResponse.getCallbackRequest(), state);
      } catch (InvalidStateException e) {
        return Flux.just(new AuthenticationFailed("State validation failed: " + e.getMessage()));
      }

    } else {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Skipping state validation, no state validator found");
      }
    }

    OauthCodeTokenRequestContext context = new OauthCodeTokenRequestContext(authorizationResponse,
        tokenEndpoint, clientConfiguration);

    return Flux.from(
            this.tokenEndpointClient.sendRequest(context))
        .switchMap(response -> {
          if (response.getRefreshToken() == null) {
            return Mono.error(new IllegalStateException("No refresh token provided"));
          }

          return authorizationResponse.getCallbackRequest()
              .getAttribute("micronaut.AUTHENTICATION", Authentication.class)
              .map(authentication -> {
                Account account = Account.fromAuthentication(authentication);

                return this.serviceAuthRepository.save(
                        new ServiceAuth(
                            new ServiceAuthId(account, clientConfiguration.getName()),
                            response.getRefreshToken()))
                    .map(serviceAuth -> AuthenticationResponse.failure());
              })
              .orElse(Mono.error(new IllegalStateException("Not authenticated")));
        });
  }
}

