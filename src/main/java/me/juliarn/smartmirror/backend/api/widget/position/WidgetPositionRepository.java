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

@R2dbcRepository(dialect = Dialect.MYSQL)
public interface WidgetPositionRepository extends
    ReactorCrudRepository<WidgetPosition, WidgetPositionId> {

  @Join(value = "account")
  Flux<WidgetPosition> findByIdAccount(@NonNull Account account);

  @SingleResult
  @Join(value = "account")
  Mono<WidgetPosition> findByIdAccountAndIdWidget(@NonNull Account account, @NonNull Widget widget);
}
