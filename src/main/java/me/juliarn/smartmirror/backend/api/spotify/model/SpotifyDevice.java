package me.juliarn.smartmirror.backend.api.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyDevice {

  private String id;
  private String name;
  private boolean isActive;
  private String type;
  private int volumePercent;

  @Creator
  public SpotifyDevice(String id, String name, boolean isActive, String type, int volumePercent) {
    this.id = id;
    this.name = name;
    this.isActive = isActive;
    this.type = type;
    this.volumePercent = volumePercent;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setIsActive(boolean active) {
    isActive = active;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getVolumePercent() {
    return this.volumePercent;
  }

  public void setVolumePercent(int volumePercent) {
    this.volumePercent = volumePercent;
  }
}
