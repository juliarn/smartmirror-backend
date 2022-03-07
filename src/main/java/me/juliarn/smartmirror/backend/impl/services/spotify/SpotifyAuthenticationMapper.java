package me.juliarn.smartmirror.backend.impl.services.spotify;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Named("spotify")
@Singleton
public class SpotifyAuthenticationMapper implements OauthAuthenticationMapper {

  @Override
  public Publisher<AuthenticationResponse> createAuthenticationResponse(
      TokenResponse tokenResponse,
      State state) {
    return null;
  }
}
