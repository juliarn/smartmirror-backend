package me.juliarn.smartmirror.backend.spotify;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import me.juliarn.smartmirror.backend.spotify.model.SpotifyPlaybackState;
import me.juliarn.smartmirror.backend.spotify.model.SpotifyUserInfo;
import org.reactivestreams.Publisher;

@Header(name = "User-Agent", value = "SmartMirror-Backend")
@Client("https://api.spotify.com/v1/me")
public interface SpotifyApiClient {

  @Get("/")
  Publisher<SpotifyUserInfo> getUserInfo(@Header("Authorization") String authorization);

  @Get("/player")
  Publisher<SpotifyPlaybackState> getPlayingState(@Header("Authorization") String authorization);
}
