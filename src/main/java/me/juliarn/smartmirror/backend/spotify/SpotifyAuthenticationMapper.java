package me.juliarn.smartmirror.backend.spotify;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.List;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Named("spotify")
@Singleton
public class SpotifyAuthenticationMapper implements OauthAuthenticationMapper {

  private static final List<String> ROLES = Collections.singletonList("ROLE_SPOTIFY");

  private final SpotifyApiClient spotifyApiClient;

  @Inject
  SpotifyAuthenticationMapper(SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  @Override
  public Publisher<AuthenticationResponse> createAuthenticationResponse(
      TokenResponse tokenResponse,
      State state) {
    return Flux.from(
            this.spotifyApiClient.getPlayingState("Bearer " + tokenResponse.getAccessToken()))
        .map(playbackState ->
            AuthenticationResponse.success(playbackState.getDevice().getName(), ROLES));
  }
}
