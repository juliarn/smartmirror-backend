package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;

public record DefaultWidgetSetting(@NonNull String settingName, @NonNull String defaultValue) {

}
