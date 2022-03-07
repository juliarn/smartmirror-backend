package me.juliarn.smartmirror.backend.api.services.spotify;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class SpotifyApiJacksonModule extends SimpleModule {

  @Override
  public void setupModule(SetupContext context) {
    context.setNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }
}
