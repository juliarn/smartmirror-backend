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
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

@Singleton
@Replaces(DefaultOauthAuthorizationResponseHandler.class)
public class CustomOauthAuthorizationResponseHandler implements OauthAuthorizationResponseHandler {

  private static final Logger LOG = LoggerFactory.getLogger(
      CustomOauthAuthorizationResponseHandler.class);

  private final TokenEndpointClient tokenEndpointClient;

  @Nullable
  private final StateValidator stateValidator;

  CustomOauthAuthorizationResponseHandler(
      TokenEndpointClient tokenEndpointClient,
      @Nullable StateValidator stateValidator) {
    this.tokenEndpointClient = tokenEndpointClient;
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
      state = null;
      if (LOG.isTraceEnabled()) {
        LOG.trace("Skipping state validation, no state validator found");
      }
    }

    OauthCodeTokenRequestContext context = new OauthCodeTokenRequestContext(authorizationResponse,
        tokenEndpoint, clientConfiguration);

    return Flux.from(
            this.tokenEndpointClient.sendRequest(context))
        .switchMap(response -> {
          Authentication authentication = authorizationResponse.getCallbackRequest()
              .getAttribute("micronaut.AUTHENTICATION", Authentication.class).orElse(null);

          if (LOG.isTraceEnabled()) {
            LOG.trace("Token endpoint returned a success response. Creating a user details");
          }
          return Flux.from(authenticationMapper.createAuthenticationResponse(response, state))
              .map(AuthenticationResponse.class::cast);
        });
  }
}

