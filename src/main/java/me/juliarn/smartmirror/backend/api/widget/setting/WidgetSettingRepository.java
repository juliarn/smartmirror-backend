package me.juliarn.smartmirror.backend.api.widget.setting;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import reactor.core.publisher.Flux;

/**
 * Repository which holds all settings of all widgets and all accounts.
 */
@R2dbcRepository(dialect = Dialect.MYSQL)
public interface WidgetSettingRepository extends
    ReactorCrudRepository<WidgetSetting, WidgetSettingId> {

  /**
   * Finds all settings related to a certain account. This includes the settings of every widget.
   *
   * @param account The account which the settings belong to
   * @return A publisher which emits all settings of the account, if any
   */
  @Join(value = "account")
  Flux<WidgetSetting> findByIdAccount(@NonNull Account account);

  /**
   * Finds all settings related to a certain account and widget.
   *
   * @param account The account which the settings belong to
   * @param widget The widget which owns the settings
   * @return A publisher which emits all settings of the account and widget, if any
   */
  @Join(value = "account")
  Flux<WidgetSetting> findByIdAccountAndIdWidget(@NonNull Account account, @NonNull Widget widget);
}
