package me.juliarn.smartmirror.backend.impl.services.spotify;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.auth.ServiceTokenProvider;
import me.juliarn.smartmirror.backend.api.services.spotify.SpotifyApiClient;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyState;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller("/api/services/spotify")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class SpotifyController {

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
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return this.serviceTokenProvider.getToken("spotify", account)
        .flatMap(this.spotifyApiClient::getPlayingState);
  }
}
