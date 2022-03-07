package me.juliarn.smartmirror.backend.impl.widget;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPosition;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPosition.PositionArea;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPositionRepository;
import me.juliarn.smartmirror.backend.api.widget.setting.DefaultWidgetSetting;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSetting;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSettingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller("api/widgets/")
@Secured(SecurityRule.IS_AUTHENTICATED)
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
            this::positionToResponse);
  }

  @Post("/positions/update/{widgetName}")
  Mono<MutableHttpResponse<?>> updatePosition(
      @NonNull Authentication authentication,
      @PathVariable @NotBlank String widgetName,
      @NonNull PositionArea area,
      float x,
      float y) {
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return Mono.just(widgetName).mapNotNull(this.widgetRegistry::get)
        .flatMap(widget -> this.widgetPositionRepository.update(
            WidgetPosition.create(account, widget, area, x, y)))
        .<MutableHttpResponse<?>>map(
            widgetPosition -> HttpResponse.ok(this.positionToResponse(widgetPosition)))
        .defaultIfEmpty(HttpResponse.badRequest(new JsonError("Invalid widget supplied")));
  }

  private Map<String, Object> positionToResponse(WidgetPosition widgetPosition) {
    return Map.of(
        "area",
        widgetPosition.area(),
        "x",
        widgetPosition.x(),
        "y",
        widgetPosition.y());
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
            this::settingToResponse);
  }

  @Post("/settings/update/{widgetName}")
  Mono<MutableHttpResponse<?>> updateSetting(
      @NonNull Authentication authentication,
      @PathVariable @NotBlank String widgetName,
      @NotBlank String settingName,
      @NonNull String value) {
    UUID accountId = UUID.fromString(authentication.getName());
    Account account = new Account(accountId);

    return Mono.just(widgetName).mapNotNull(this.widgetRegistry::get)
        .flatMap(widget -> {
          Optional<DefaultWidgetSetting> defaultSetting = widget.defaultSettings().stream()
              .filter(setting -> setting.settingName().equals(settingName))
              .findFirst();

          if (defaultSetting.isPresent()) {
            Collection<String> acceptedValues = defaultSetting.get().acceptedValues();
            if (acceptedValues != null && !acceptedValues.contains(value)) {
              return Mono.just(HttpResponse.badRequest(new JsonError("Illegal value supplied")));
            }

            return this.widgetSettingRepository.update(
                    WidgetSetting.create(account, widget, settingName, value))
                .map(widgetSetting -> HttpResponse.ok(this.settingToResponse(widgetSetting)));
          } else {
            return Mono.just(
                HttpResponse.badRequest(new JsonError("Invalid setting name supplied")));
          }
        })
        .defaultIfEmpty(HttpResponse.badRequest(new JsonError("Invalid widget supplied")));
  }

  private Map<String, Object> settingToResponse(WidgetSetting widgetSetting) {
    return Map.of(
        "settingName",
        widgetSetting.id().settingName(),
        "value",
        String.valueOf(widgetSetting.value()));
  }
}
