package me.juliarn.smartmirror.backend.spotify;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.spotify.model.SpotifyPlaybackState;
import org.reactivestreams.Publisher;

import javax.validation.constraints.NotNull;

@Controller("/api/spotify")
@Secured("ROLE_SPOTIFY")
public class SpotifyController {

  private final SpotifyApiClient spotifyApiClient;

  @Inject
  SpotifyController(SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  @Get(value = "/playback", produces = MediaType.APPLICATION_JSON_STREAM)
  public Publisher<SpotifyPlaybackState> getPlaybackState(@NotNull Authentication authentication) {
    return this.spotifyApiClient.getPlayingState(
        "Bearer " + authentication.getAttributes().get("spotifyToken"));
  }
}
