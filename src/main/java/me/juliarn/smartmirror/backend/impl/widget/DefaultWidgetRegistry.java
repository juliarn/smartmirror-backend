package me.juliarn.smartmirror.backend.impl.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.setting.DefaultWidgetSetting;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class DefaultWidgetRegistry implements WidgetRegistry {

  private final Map<String, Widget> widgets;

  @Inject
  DefaultWidgetRegistry() {
    this.widgets = new HashMap<>();

    this.register(new Widget("weather", "Weather", Set.of(
        new DefaultWidgetSetting("language", "Language", "en"),
        new DefaultWidgetSetting(
            "tempUnit",
            "Temperature unit",
            "metric",
            Set.of("standard", "metric", "imperial"))),
        false));
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
    return Set.copyOf(this.widgets.values());
  }
}
