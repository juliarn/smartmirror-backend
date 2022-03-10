package me.juliarn.smartmirror.backend.api.services.weather.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
public final class WeatherState {

  private WeatherInfo[] weather;
  private MainWeatherInfo main;
  private SystemWeatherInfo sys;
  private String name;
  private long dt;

  @Creator
  public WeatherState(
      WeatherInfo[] weather,
      MainWeatherInfo main,
      SystemWeatherInfo sys,
      String name,
      long dt) {
    this.weather = weather;
    this.main = main;
    this.sys = sys;
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

  public SystemWeatherInfo getSys() {
    return this.sys;
  }

  public void setSys(SystemWeatherInfo sys) {
    this.sys = sys;
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

  @Introspected
  public static final class SystemWeatherInfo {

    private String country;
    private long sunrise;
    private long sunset;

    @Creator
    public SystemWeatherInfo(String country, long sunrise, long sunset) {
      this.country = country;
      this.sunrise = sunrise;
      this.sunset = sunset;
    }

    public String getCountry() {
      return this.country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public long getSunrise() {
      return this.sunrise;
    }

    public void setSunrise(long sunrise) {
      this.sunrise = sunrise;
    }

    public long getSunset() {
      return this.sunset;
    }

    public void setSunset(long sunset) {
      this.sunset = sunset;
    }
  }
}
