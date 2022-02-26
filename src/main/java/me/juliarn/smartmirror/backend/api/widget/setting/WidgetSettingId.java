package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.Relation.Kind;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;

@Embeddable
public record WidgetSettingId(
    @Relation(value = Kind.MANY_TO_ONE) @NonNull Account account,
    @NonNull Widget widget,
    @NonNull String settingName) {

}
