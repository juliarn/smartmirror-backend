package me.juliarn.smartmirror.backend.impl.services.spotify;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import javax.validation.constraints.NotNull;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceTokenProvider;
import me.juliarn.smartmirror.backend.api.services.spotify.SpotifyApiClient;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyState;
import reactor.core.publisher.Mono;

@Controller("/api/services/spotify")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class SpotifyController {

  private static final SpotifyState DEFAULT_SPOTIFY_STATE = new SpotifyState(
      null,
      null,
      false,
      null);

  private final ServiceTokenProvider serviceTokenProvider;
  private final SpotifyApiClient spotifyApiClient;

  @Inject
  SpotifyController(
      ServiceTokenProvider serviceTokenProvider,
      SpotifyApiClient spotifyApiClient) {
    this.serviceTokenProvider = serviceTokenProvider;
    this.spotifyApiClient = spotifyApiClient;
  }

  @Get("/state")
  Mono<SpotifyState> getSpotifyState(@NotNull Authentication authentication) {
    Account account = Account.fromAuthentication(authentication);

    return this.serviceTokenProvider.getToken("spotify", account)
        .flatMap(token -> this.spotifyApiClient.getPlayingState("Bearer " + token))
        .onErrorReturn(DEFAULT_SPOTIFY_STATE);
  }
}
