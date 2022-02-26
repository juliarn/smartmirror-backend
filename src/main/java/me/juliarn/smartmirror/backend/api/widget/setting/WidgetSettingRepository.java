package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface WidgetSettingRepository extends
    ReactorCrudRepository<WidgetSetting, WidgetSettingId> {

  @Join(value = "account")
  Flux<WidgetSetting> findByIdAccount(@NonNull Account account);

  @Join(value = "account")
  Flux<WidgetSetting> findByIdAccountAndIdWidget(@NonNull Account account, @NonNull Widget widget);
}
