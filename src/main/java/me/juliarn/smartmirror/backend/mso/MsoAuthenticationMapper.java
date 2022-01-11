package me.juliarn.smartmirror.backend.mso;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named("mso")
@Singleton
public class MsoAuthenticationMapper implements OpenIdAuthenticationMapper {

  @Override
  public AuthenticationResponse createAuthenticationResponse(
      String providerName,
      OpenIdTokenResponse tokenResponse,
      OpenIdClaims openIdClaims,
      State state) {
    // TODO: 11.01.22
    return AuthenticationResponse.success("name");
  }
}
