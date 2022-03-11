package me.juliarn.smartmirror.backend.api.widget.position;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository which holds all widget positions, related to an account and a widget.
 */
@R2dbcRepository(dialect = Dialect.MYSQL)
public interface WidgetPositionRepository extends
    ReactorCrudRepository<WidgetPosition, WidgetPositionId> {

  /**
   * Finds all positions of all widgets related to a certain account.
   *
   * @param account The account which is related to the positions
   * @return A publisher which emits all positions, if any
   */
  @Join(value = "account")
  Flux<WidgetPosition> findByIdAccount(@NonNull Account account);

  /**
   * Finds a specific widget position related to a certain account.
   *
   * @param account The account the position is related to.
   * @param widget The widget the position belongs to
   * @return A publisher which emits the position of the widget or null, if none
   */
  @SingleResult
  @Join(value = "account")
  Mono<WidgetPosition> findByIdAccountAndIdWidget(@NonNull Account account, @NonNull Widget widget);
}
