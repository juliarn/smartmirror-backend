package me.juliarn.smartmirror.backend.api.services.spotify;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyUserInfo;
import me.juliarn.smartmirror.backend.api.services.spotify.model.SpotifyPlaybackState;
import reactor.core.publisher.Mono;

@Header(name = "User-Agent", value = "SmartMirror-Backend")
@Client("https://api.spotify.com/v1/me")
public interface SpotifyApiClient {

  @Get("/")
  Mono<SpotifyUserInfo> getUserInfo(@Header("Authorization") String authorization);

  @Get("/player")
  Mono<SpotifyPlaybackState> getPlayingState(@Header("Authorization") String authorization);
}
