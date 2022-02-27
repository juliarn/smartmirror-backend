package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public record DefaultWidgetSetting(@NonNull String settingName, @NonNull String defaultValue) {

  @Creator
  public DefaultWidgetSetting {
  }
}
