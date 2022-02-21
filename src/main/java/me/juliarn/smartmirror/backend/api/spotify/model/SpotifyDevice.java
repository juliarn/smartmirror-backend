package me.juliarn.smartmirror.backend.api.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifyDevice(String id,
                            String name,
                            boolean isActive,
                            String type,
                            int volumePercent) {

  @Creator
  public SpotifyDevice {
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public String getType() {
    return this.type;
  }

  public int getVolumePercent() {
    return this.volumePercent;
  }
}
