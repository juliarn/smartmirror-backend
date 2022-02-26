package me.juliarn.smartmirror.backend.api.widget;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.data.model.runtime.convert.AttributeConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class WidgetAttributeConverter implements AttributeConverter<Widget, String> {

  private final WidgetRegistry widgetRegistry;

  @Inject
  WidgetAttributeConverter(WidgetRegistry widgetRegistry) {
    this.widgetRegistry = widgetRegistry;
  }

  @Override
  @Nullable
  public String convertToPersistedValue(@Nullable Widget entityValue, ConversionContext context) {
    return entityValue == null ? null : entityValue.name();
  }

  @Override
  @Nullable
  public Widget convertToEntityValue(@Nullable String persistedValue, ConversionContext context) {
    return persistedValue == null ? null : this.widgetRegistry.get(persistedValue);
  }
}
