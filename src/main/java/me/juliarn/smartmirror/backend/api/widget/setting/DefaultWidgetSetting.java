package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import java.util.Collection;

@Introspected
public record DefaultWidgetSetting(
    @NonNull String settingName,
    @NonNull String defaultValue,
    @Nullable Collection<String> acceptedValues) {

  @Creator
  public DefaultWidgetSetting {
  }

  public DefaultWidgetSetting(@NonNull String settingName, @NonNull String defaultValue) {
    this(settingName, defaultValue, null);
  }
}
