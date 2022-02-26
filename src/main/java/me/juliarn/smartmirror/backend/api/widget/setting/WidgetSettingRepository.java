package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface WidgetSettingRepository extends
    ReactiveStreamsCrudRepository<WidgetSetting, WidgetSettingId> {

  @Override
  @NonNull
  @SingleResult
  Mono<WidgetSetting> save(@Valid @NotNull @NonNull WidgetSetting entity);

  @Join(value = "account")
  Flux<WidgetSetting> findByIdAccount(@NonNull Account account);

  @Join(value = "account")
  Flux<WidgetSetting> findByIdAccountAndIdWidget(@NonNull Account account, @NonNull Widget widget);
}
