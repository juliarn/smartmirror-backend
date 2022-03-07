package me.juliarn.smartmirror.backend.api.services.spotify;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.jackson.annotation.JacksonFeatures;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyState;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyUserInfo;
import reactor.core.publisher.Mono;

@Header(name = "User-Agent", value = "SmartMirror-Backend")
@Client("https://api.spotify.com/v1/me")
@JacksonFeatures(additionalModules = SpotifyApiJacksonModule.class)
public interface SpotifyApiClient {

  @Get("/")
  Mono<SpotifyUserInfo> getUserInfo(@Header("Authorization") String authorization);

  @Get("/player")
  Mono<SpotifyState> getPlayingState(@Header("Authorization") String authorization);
}
