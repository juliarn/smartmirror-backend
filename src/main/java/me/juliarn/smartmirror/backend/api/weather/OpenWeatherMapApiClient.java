package me.juliarn.smartmirror.backend.api.weather;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;
import me.juliarn.smartmirror.backend.api.weather.model.WeatherState;
import org.reactivestreams.Publisher;

@Header(name = "User-Agent", value = "SmartMirror-Backend")
@Client("https://api.openweathermap.org/data/2.5")
public interface OpenWeatherMapApiClient {

  @Get("/weather?lat=${lat}&lon=${lon}&cnt=1&units=${unit}&lang=${lang}&appid=${token}")
  Publisher<Void> getWeather(
      @PathVariable float lat,
      @PathVariable float lon,
      @PathVariable String lang,
      @PathVariable String unit,
      @PathVariable String token); // TODO: 22.02.2022  

  @Get("/forecast?lat=${lat}&lon=${lon}&cnt=6&units=${unit}&lang=${lang}&appid=${token}")
  Publisher<WeatherState> getForecast(
      @PathVariable float lat,
      @PathVariable float lon,
      @PathVariable String lang,
      @PathVariable String unit,
      @PathVariable String token);
}
