package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity(value = "widget_setting")
public record WidgetSetting(
    @EmbeddedId @NonNull WidgetSettingId id,
    @Nullable String value) {

}
