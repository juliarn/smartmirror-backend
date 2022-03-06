package me.juliarn.smartmirror.backend.impl.services.weather;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.services.weather.OpenWeatherMapApiClient;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherState;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherState.WeatherInfo;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/api/services/weather")
@Secured(Roles.ACCOUNT)
public class WeatherController {

  private static final String OPEN_WEATHER_MAP_ICON_URL_TEMPLATE = "http://openweathermap.org/img/wn/%s@2x.png";

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
        .map(tuple -> Map.of(
            "current",
            this.mapToFullIconUrl(tuple.getT1()),
            "forecast",
            Arrays.stream(tuple.getT2().getList()).map(this::mapToFullIconUrl)
                .collect(Collectors.toList())));
  }

  WeatherState mapToFullIconUrl(WeatherState weatherState) {
    for (WeatherInfo weatherInfo : weatherState.getWeather()) {
      weatherInfo.setIcon(OPEN_WEATHER_MAP_ICON_URL_TEMPLATE.formatted(weatherInfo.getIcon()));
    }
    return weatherState;
  }
}
