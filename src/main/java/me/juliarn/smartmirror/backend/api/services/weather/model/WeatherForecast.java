package me.juliarn.smartmirror.backend.api.services.weather.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(SnakeCaseStrategy.class)
public final class WeatherForecast {

  private WeatherState[] list;

  @Creator
  public WeatherForecast(WeatherState[] list) {
    this.list = list;
  }

  public WeatherState[] getList() {
    return this.list;
  }

  public void setList(WeatherState[] list) {
    this.list = list;
  }
}
