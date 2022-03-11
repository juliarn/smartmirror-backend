package me.juliarn.smartmirror.backend.api.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import java.util.Collection;

/**
 * Contains all currently registered widgets.
 */
public interface WidgetRegistry {

  /**
   * Registers a new widget.
   *
   * @param widget The widget to register
   */
  void register(@NonNull Widget widget);

  /**
   * Returns a certain widget by name.
   *
   * @param name The name of the widget
   * @return The widget or null, if no widget with this name present
   */
  @Nullable
  Widget get(@NonNull String name);

  /**
   * @return A copy of all currently registered widgets
   */
  @NonNull
  Collection<Widget> getWidgets();
}
