package me.juliarn.smartmirror.backend.impl.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPosition;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPositionRepository;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSetting;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSettingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Controller("api/widgets/")
@Secured(Roles.ACCOUNT)
public class WidgetController {

  private final WidgetRegistry widgetRegistry;
  private final WidgetSettingRepository widgetSettingRepository;
  private final WidgetPositionRepository widgetPositionRepository;

  @Inject
  WidgetController(
      WidgetRegistry widgetRegistry,
      WidgetSettingRepository widgetSettingRepository,
      WidgetPositionRepository widgetPositionRepository) {
    this.widgetRegistry = widgetRegistry;
    this.widgetSettingRepository = widgetSettingRepository;
    this.widgetPositionRepository = widgetPositionRepository;
  }

  @Get
  Collection<Widget> getWidgets() {
    return this.widgetRegistry.getWidgets();
  }

  @Get("/positions")
  Mono<?> getPositions(@NonNull Authentication authentication) {
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return Flux.fromIterable(this.widgetRegistry.getWidgets())
        // Find the saved position for a certain widget
        .flatMap(widget -> this.widgetPositionRepository.findByIdAccountAndIdWidget(account, widget)
            // If the position is not present, save the default position
            .switchIfEmpty(
                this.widgetPositionRepository.save(WidgetPosition.createDefault(account, widget))))
        // Collect in a multimap, creating a user-friendly data structure
        .collectMap(
            widgetPosition -> widgetPosition.id().widget().name(),
            widgetPosition -> Map.of(
                "area",
                widgetPosition.area(),
                "x",
                widgetPosition.x(),
                "y",
                widgetPosition.y()));
  }

  @Get("/settings")
  Mono<?> getSettings(@NonNull Authentication authentication) {
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return Flux.fromIterable(this.widgetRegistry.getWidgets())
        // Find all saved settings for a certain widget
        .flatMap(widget -> this.widgetSettingRepository.findByIdAccountAndIdWidget(account, widget)
            // If no settings present, save all default settings for that widget
            .switchIfEmpty(Flux.fromIterable(widget.defaultSettings())
                .flatMap(defaultSetting -> this.widgetSettingRepository.save(
                    WidgetSetting.createDefault(account, widget, defaultSetting)))))
        // Collect in a multimap, creating a user-friendly data structure
        .collectMultimap(
            widgetSetting -> widgetSetting.id().widget().name(),
            widgetSetting -> Map.of(
                "name",
                widgetSetting.id().settingName(),
                "value",
                String.valueOf(widgetSetting.value())));
  }
}
