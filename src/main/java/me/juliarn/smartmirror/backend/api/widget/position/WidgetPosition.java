package me.juliarn.smartmirror.backend.api.widget.position;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;

@MappedEntity("widget_position")
public record WidgetPosition(
    @EmbeddedId @NonNull WidgetPositionId id,
    @NonNull PositionArea area,
    float x,
    float y) {

  public static WidgetPosition createDefault(@NonNull Account account, @NonNull Widget widget) {
    return new WidgetPosition(new WidgetPositionId(account, widget), widget.defaultArea(), 0F, 0F);
  }

  public static WidgetPosition create(
      @NonNull Account account,
      @NonNull Widget widget,
      @NonNull PositionArea area,
      float x,
      float y) {
    return new WidgetPosition(new WidgetPositionId(account, widget), area, x, y);
  }

  public enum PositionArea {
    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,
    MIDDLE_LEFT,
    MIDDLE_CENTER,
    MIDDLE_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT
  }
}
