package me.juliarn.smartmirror.backend.impl.services.spotify;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import javax.validation.constraints.NotNull;
import me.juliarn.smartmirror.backend.api.services.spotify.SpotifyApiClient;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyPlaybackState;
import reactor.core.publisher.Mono;

@Controller("/api/services/spotify")
@Secured("ROLE_SPOTIFY")
public class SpotifyController {

  private final SpotifyApiClient spotifyApiClient;

  @Inject
  SpotifyController(SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  @Get(value = "/playback")
  Mono<SpotifyPlaybackState> getPlaybackState(@NotNull Authentication authentication) {
    return this.spotifyApiClient.getPlayingState(
        "Bearer " + authentication.getAttributes().get("spotifyToken"));
  }
}