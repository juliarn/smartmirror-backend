package me.juliarn.smartmirror.backend.api.services.weather;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class OpenWeatherMapApiJacksonModule extends SimpleModule {

  @Override
  public void setupModule(SetupContext context) {
    context.setNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }
}
