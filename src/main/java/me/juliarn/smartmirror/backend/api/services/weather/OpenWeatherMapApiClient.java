package me.juliarn.smartmirror.backend.api.services.weather;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.jackson.annotation.JacksonFeatures;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherForecast;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherState;
import reactor.core.publisher.Mono;

@Header(name = "User-Agent", value = "SmartMirror-Backend")
@Client("https://api.openweathermap.org/data/2.5")
@JacksonFeatures(additionalModules = OpenWeatherMapApiJacksonModule.class)
public interface OpenWeatherMapApiClient {

  @Get("/weather?lat={lat}&lon={lon}&cnt=1&units={unit}&lang={lang}&appid=${smartmirror.services.openweathermap.token}")
  Mono<WeatherState> getWeather(
      @PathVariable float lat,
      @PathVariable float lon,
      @PathVariable String lang,
      @PathVariable String unit);

  @Get("/forecast?lat={lat}&lon={lon}&cnt=6&units={unit}&lang={lang}&appid=${smartmirror.services.openweathermap.token}")
  Mono<WeatherForecast> getForecast(
      @PathVariable float lat,
      @PathVariable float lon,
      @PathVariable String lang,
      @PathVariable String unit);
}
