package me.juliarn.smartmirror.backend.impl.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.setting.DefaultWidgetSetting;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Singleton
public class DefaultWidgetRegistry implements WidgetRegistry {

  private final Map<String, Widget> widgets;

  @Inject
  DefaultWidgetRegistry() {
    this.widgets = new LinkedHashMap<>();

    this.register(new Widget(
        "time",
        "Time",
        "https://www.pinclipart.com/picdir/middle/150-1502851_png-file-svg-calendar-time-icon-png-clipart.png",
        Set.of(),
        false));
    this.register(new Widget(
        "weather",
        "Weather",
        "https://cdn2.iconfinder.com/data/icons/weather-flat-14/64/weather02-512.png",
        Set.of(
            new DefaultWidgetSetting(
                "tempUnit",
                "Temperature unit",
                "metric",
                Set.of("standard", "metric", "imperial"))),
        false));
    this.register(new Widget(
        "mso",
        "MSO",
        "https://www.marienschule.com/assets/MSOLogo-84510ebbb836300f53362971b6f5fe13d66642acf46fe82d95e4ee4fb65822b5.svg",
        Set.of(
            new DefaultWidgetSetting(
                "userId",
                "User Id",
                ""),
            new DefaultWidgetSetting(
                "coverLessonTitle",
                "Cover lessons title",
                "Cover lessons for today")),
        false));
    this.register(new Widget(
        "spotify",
        "Spotify",
        "https://www.freepnglogos.com/uploads/spotify-logo-png/spotify-download-logo-30.png",
        Set.of(
            new DefaultWidgetSetting("deviceLabel", "Device Label", "Music on %device%")),
        true));
  }

  @Override
  public void register(@NonNull Widget widget) {
    this.widgets.put(widget.name(), widget);
  }

  @Override
  @Nullable
  public Widget get(@NonNull String name) {
    return this.widgets.get(name);
  }

  @Override
  @NonNull
  public Collection<Widget> getWidgets() {
    return List.copyOf(this.widgets.values());
  }
}
