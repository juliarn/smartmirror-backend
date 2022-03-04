package me.juliarn.smartmirror.backend.api.widget;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import me.juliarn.smartmirror.backend.api.widget.setting.DefaultWidgetSetting;

import java.util.Collection;

@Introspected
@TypeDef(type = DataType.STRING, converter = WidgetAttributeConverter.class)
public record Widget(@NonNull String name,
                     @NonNull String displayName,
                     @NonNull Collection<DefaultWidgetSetting> defaultSettings,
                     boolean requiresServiceAuth) {

  @Creator
  public Widget {
  }
}
