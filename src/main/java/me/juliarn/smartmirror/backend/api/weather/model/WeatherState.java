package me.juliarn.smartmirror.backend.api.weather.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonNaming(SnakeCaseStrategy.class)
public final class WeatherState {

  private WeatherInfo[] weather;
  private MainWeatherInfo main;
  private String name;
  private long dt;

  @Creator
  public WeatherState(WeatherInfo[] weather, MainWeatherInfo main, String name, long dt) {
    this.weather = weather;
    this.main = main;
    this.name = name;
    this.dt = dt;
  }

  public WeatherInfo[] getWeather() {
    return this.weather;
  }

  public void setWeather(WeatherInfo[] weather) {
    this.weather = weather;
  }

  public MainWeatherInfo getMain() {
    return this.main;
  }

  public void setMain(MainWeatherInfo main) {
    this.main = main;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getDt() {
    return this.dt;
  }

  public void setDt(long dt) {
    this.dt = dt;
  }

  @Introspected
  @JsonNaming(SnakeCaseStrategy.class)
  public static final class WeatherInfo {

    private String main;
    private String description;
    private String icon;

    @Creator
    public WeatherInfo(String main, String description, String icon) {
      this.main = main;
      this.description = description;
      this.icon = icon;
    }

    public String getMain() {
      return this.main;
    }

    public void setMain(String main) {
      this.main = main;
    }

    public String getDescription() {
      return this.description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getIcon() {
      return this.icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }
  }

  @Introspected
  @JsonNaming(SnakeCaseStrategy.class)
  public static final class MainWeatherInfo {

    private float temp;
    private float feelsLike;
    private float tempMin;
    private float tempMax;

    @Creator
    public MainWeatherInfo(float temp, float feelsLike, float tempMin, float tempMax) {
      this.temp = temp;
      this.feelsLike = feelsLike;
      this.tempMin = tempMin;
      this.tempMax = tempMax;
    }

    public float getTemp() {
      return this.temp;
    }

    public void setTemp(float temp) {
      this.temp = temp;
    }

    public float getFeelsLike() {
      return this.feelsLike;
    }

    public void setFeelsLike(float feelsLike) {
      this.feelsLike = feelsLike;
    }

    public float getTempMin() {
      return this.tempMin;
    }

    public void setTempMin(float tempMin) {
      this.tempMin = tempMin;
    }

    public float getTempMax() {
      return this.tempMax;
    }

    public void setTempMax(float tempMax) {
      this.tempMax = tempMax;
    }
  }
}
