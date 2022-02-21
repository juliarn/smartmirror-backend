package me.juliarn.smartmirror.backend.api.weather.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record WeatherState(
    WeatherInfo[] weather,
    MainWeatherInfo main,
    String name) {

  @Creator
  public WeatherState {
  }

  @Introspected
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record WeatherInfo(String main, String description, String icon) {

    @Creator
    public WeatherInfo {
    }
  }

  @Introspected
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record MainWeatherInfo(float temp, float feelsLike, float tempMin, float tempMax) {

    @Creator
    public MainWeatherInfo {
    }
  }
}
