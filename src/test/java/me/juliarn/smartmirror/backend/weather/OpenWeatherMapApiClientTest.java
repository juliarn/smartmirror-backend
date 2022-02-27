package me.juliarn.smartmirror.backend.weather;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.services.weather.OpenWeatherMapApiClient;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherForecast;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
public class OpenWeatherMapApiClientTest {

  private static final float BEVERLY_HILLS_LAT = 34.073620F;
  private static final float BEVERLY_HILLS_LON = -118.400352F;

  @Inject
  OpenWeatherMapApiClient client;

  @Test
  void testGetWeather() {
    WeatherState state = this.client.getWeather(
        BEVERLY_HILLS_LAT,
        BEVERLY_HILLS_LON,
        "de",
        "metric").block();

    Assertions.assertNotNull(state);
    Assertions.assertEquals("Beverly Hills", state.getName());
  }

  @Test
  void testGetWeatherForecast() {
    WeatherForecast forecast = this.client.getForecast(
        BEVERLY_HILLS_LAT,
        BEVERLY_HILLS_LON,
        "de",
        "metric").block();

    Assertions.assertNotNull(forecast);
    Assertions.assertEquals(6, forecast.getList().length);
  }
}
