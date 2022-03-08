package me.juliarn.smartmirror.backend.impl.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPosition.PositionArea;
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
        "weather",
        "Weather",
        Set.of(
            new DefaultWidgetSetting(
                "tempUnit",
                "Temperature unit",
                "metric",
                Set.of("standard", "metric", "imperial"))),
        PositionArea.TOP_LEFT,
        false));
    this.register(new Widget(
        "time",
        "Time",
        Set.of(
            new DefaultWidgetSetting(
                "showSeconds",
                "Show seconds",
                "false",
                Set.of("true", "false")),
            new DefaultWidgetSetting(
                "showYear",
                "Show year",
                "true",
                Set.of("true", "false"))),
        PositionArea.TOP_RIGHT,
        false));
    this.register(new Widget(
        "mso",
        "MSO",
        Set.of(
            new DefaultWidgetSetting(
                "userId",
                "User Id",
                ""),
            new DefaultWidgetSetting(
                "coverLessonTitle",
                "Cover lessons title",
                "Cover lessons for today")),
        PositionArea.BOTTOM_LEFT,
        false));
    this.register(new Widget(
        "spotify",
        "Spotify",
        Set.of(
            new DefaultWidgetSetting("deviceLabel", "Device Label", "Music on %device%")),
        PositionArea.BOTTOM_RIGHT,
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
