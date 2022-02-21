package me.juliarn.smartmirror.backend.api.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifyPlaybackState(SpotifyDevice device,
                                   Integer progressMs,
                                   boolean isPlaying,
                                   SpotifySongItem item) {

  @Creator
  public SpotifyPlaybackState {
  }

  public SpotifyDevice getDevice() {
    return this.device;
  }

  public Integer getProgressMs() {
    return this.progressMs;
  }

  public boolean isIsPlaying() {
    return this.isPlaying;
  }

  public SpotifySongItem getItem() {
    return this.item;
  }
}
