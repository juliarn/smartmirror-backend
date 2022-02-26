package me.juliarn.smartmirror.backend.api.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import java.util.Collection;

public interface WidgetRegistry {

  void register(@NonNull Widget widget);

  @Nullable
  Widget get(@NonNull String name);

  @NonNull
  Collection<Widget> getWidgets();
}
