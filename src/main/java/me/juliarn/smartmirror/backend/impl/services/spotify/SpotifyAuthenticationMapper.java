package me.juliarn.smartmirror.backend.impl.services.spotify;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Map;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.services.spotify.SpotifyApiClient;
import org.reactivestreams.Publisher;

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
    return this.spotifyApiClient.getUserInfo("Bearer " + tokenResponse.getAccessToken())
        .map(spotifyUserInfo -> AuthenticationResponse.success(
            spotifyUserInfo.getId(),
            List.of(Roles.SPOTIFY),
            Map.of(
                "spotifyDisplayName", spotifyUserInfo.getDisplayName(),
                "spotifyToken", tokenResponse.getAccessToken())));
  }
}
