package me.juliarn.smartmirror.backend.impl.services.weather;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.services.weather.OpenWeatherMapApiClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller("/api/services/weather")
@Secured(Roles.ACCOUNT)
public class WeatherController {

  private final OpenWeatherMapApiClient openWeatherMapApiClient;

  @Inject
  public WeatherController(OpenWeatherMapApiClient openWeatherMapApiClient) {
    this.openWeatherMapApiClient = openWeatherMapApiClient;
  }

  @Post("/state")
  Mono<Map<String, Object>> getState(
      float lat,
      float lon,
      @NonNull String lang,
      @NonNull String unit) {
    return Mono.zip(
            this.openWeatherMapApiClient.getWeather(lat, lon, lang, unit),
            this.openWeatherMapApiClient.getForecast(lat, lon, lang, unit))
        .map(tuple -> Map.of("current", tuple.getT1(), "forecast", tuple.getT2().getList()));
  }
}
