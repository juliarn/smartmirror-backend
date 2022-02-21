package me.juliarn.smartmirror.backend.impl.spotify;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.spotify.SpotifyApiClient;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Named("spotify")
@Singleton
public class SpotifyAuthenticationMapper implements OauthAuthenticationMapper {

  private final SpotifyApiClient spotifyApiClient;

  @Inject
  SpotifyAuthenticationMapper(SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  @Override
  public Publisher<AuthenticationResponse> createAuthenticationResponse(
      TokenResponse tokenResponse,
      State state) {
    return Flux.from(this.spotifyApiClient.getUserInfo("Bearer " + tokenResponse.getAccessToken()))
        .map(spotifyUserInfo -> AuthenticationResponse.success(
            spotifyUserInfo.id(),
            List.of(Roles.SPOTIFY),
            Map.of(
                "spotifyDisplayName", spotifyUserInfo.displayName(),
                "spotifyToken", tokenResponse.getAccessToken())));
  }
}
