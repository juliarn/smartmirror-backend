package me.juliarn.smartmirror.backend.api.spotify.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifyUserInfo(String displayName, String id, String product) {

  @Creator
  public SpotifyUserInfo {
  }
}
