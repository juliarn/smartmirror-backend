package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;

@MappedEntity("widget_setting")
public record WidgetSetting(
    @EmbeddedId @NonNull WidgetSettingId id,
    @Nullable String value) {

  public static WidgetSetting createDefault(
      @NonNull Account account,
      @NonNull Widget widget,
      @NonNull DefaultWidgetSetting defaultSetting) {
    return new WidgetSetting(
        new WidgetSettingId(account, widget, defaultSetting.settingName()),
        defaultSetting.defaultValue());
  }

  public static WidgetSetting create(
      @NonNull Account account,
      @NonNull Widget widget,
      @NonNull String settingName,
      @NonNull String value) {
    return new WidgetSetting(
        new WidgetSettingId(account, widget, settingName),
        value);
  }
}
