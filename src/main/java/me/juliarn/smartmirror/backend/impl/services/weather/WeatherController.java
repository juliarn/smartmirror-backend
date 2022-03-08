package me.juliarn.smartmirror.backend.impl.services.weather;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.weather.OpenWeatherMapApiClient;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherState;
import me.juliarn.smartmirror.backend.api.services.weather.model.WeatherState.WeatherInfo;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSettingRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/api/services/weather")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class WeatherController {

  private static final String WEATHER_WIDGET_NAME = "weather";
  private static final String TEMP_UNIT_SETTING_NAME = "tempUnit";
  private static final String OPEN_WEATHER_MAP_ICON_URL_TEMPLATE = "http://openweathermap.org/img/wn/%s@2x.png";

  private final Widget weatherWidget;
  private final WidgetSettingRepository widgetSettingRepository;
  private final OpenWeatherMapApiClient openWeatherMapApiClient;

  @Inject
  public WeatherController(
      WidgetRegistry widgetRegistry,
      WidgetSettingRepository widgetSettingRepository,
      OpenWeatherMapApiClient openWeatherMapApiClient) {
    this.weatherWidget = widgetRegistry.get(WEATHER_WIDGET_NAME);
    this.widgetSettingRepository = widgetSettingRepository;
    this.openWeatherMapApiClient = openWeatherMapApiClient;
  }

  @Post("/state")
  Publisher<Map<String, Object>> getWeatherState(
      @NonNull Authentication authentication,
      float lat,
      float lon,
      @NonNull String lang) {
    return Mono.from(this.widgetSettingRepository.findByIdAccountAndIdWidget(
                Account.fromAuthentication(authentication),
                this.weatherWidget)
            .filter(widgetSetting -> TEMP_UNIT_SETTING_NAME.equals(widgetSetting.id().settingName())))
        .flatMap(widgetSetting -> Mono.zip(
                this.openWeatherMapApiClient.getWeather(lat, lon, lang, widgetSetting.value()),
                this.openWeatherMapApiClient.getForecast(lat, lon, lang, widgetSetting.value()))
            .map(tuple -> Map.of(
                "current",
                this.mapToFullIconUrl(tuple.getT1()),
                "forecast",
                Arrays.stream(tuple.getT2().getList()).map(this::mapToFullIconUrl)
                    .collect(Collectors.toList()))));
  }

  WeatherState mapToFullIconUrl(WeatherState weatherState) {
    for (WeatherInfo weatherInfo : weatherState.getWeather()) {
      weatherInfo.setIcon(OPEN_WEATHER_MAP_ICON_URL_TEMPLATE.formatted(weatherInfo.getIcon()));
    }
    return weatherState;
  }
}
