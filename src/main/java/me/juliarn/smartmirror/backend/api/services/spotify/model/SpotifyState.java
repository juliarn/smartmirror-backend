package me.juliarn.smartmirror.backend.api.services.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(SnakeCaseStrategy.class)
public final class SpotifyState {

  private SpotifyDevice device;
  private Integer progressMs;
  private boolean isPlaying;
  private SpotifySongItem item;

  @Creator
  public SpotifyState(
      SpotifyDevice device,
      Integer progressMs,
      boolean isPlaying,
      SpotifySongItem item) {
    this.device = device;
    this.progressMs = progressMs;
    this.isPlaying = isPlaying;
    this.item = item;
  }

  public SpotifyDevice getDevice() {
    return this.device;
  }

  public void setDevice(SpotifyDevice device) {
    this.device = device;
  }

  public Integer getProgressMs() {
    return this.progressMs;
  }

  public void setProgressMs(Integer progressMs) {
    this.progressMs = progressMs;
  }

  public boolean isPlaying() {
    return this.isPlaying;
  }

  public void setPlaying(boolean playing) {
    isPlaying = playing;
  }

  public SpotifySongItem getItem() {
    return this.item;
  }

  public void setItem(SpotifySongItem item) {
    this.item = item;
  }
}