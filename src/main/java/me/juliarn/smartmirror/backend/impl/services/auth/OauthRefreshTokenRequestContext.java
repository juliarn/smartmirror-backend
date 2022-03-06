package me.juliarn.smartmirror.backend.impl.services.auth;


import io.micronaut.core.type.Argument;
import io.micronaut.http.MediaType;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.security.oauth2.endpoint.SecureEndpoint;
import io.micronaut.security.oauth2.endpoint.token.request.context.AbstractTokenRequestContext;
import io.micronaut.security.oauth2.endpoint.token.response.TokenErrorResponse;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import java.util.Map;

public class OauthRefreshTokenRequestContext extends AbstractTokenRequestContext<Map<String, String>, TokenResponse> {

  private final String refreshToken;

  public OauthRefreshTokenRequestContext(
      String refreshToken,
      SecureEndpoint tokenEndpoint,
      OauthClientConfiguration clientConfiguration) {
    super(MediaType.APPLICATION_FORM_URLENCODED_TYPE, tokenEndpoint, clientConfiguration);
    this.refreshToken = refreshToken;
  }

  @Override
  public Map<String, String> getGrant() {
    RefreshTokenGrant refreshTokenGrant = new RefreshTokenGrant();
    refreshTokenGrant.setRefreshToken(this.refreshToken);
    return refreshTokenGrant.toMap();
  }

  @Override
  public Argument<TokenResponse> getResponseType() {
    return Argument.of(TokenResponse.class);
  }

  @Override
  public Argument<?> getErrorResponseType() {
    return Argument.of(TokenErrorResponse.class);
  }
}
