package me.juliarn.smartmirror.backend.widget;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Set;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.account.AccountRepository;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPosition;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPosition.PositionArea;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPositionId;
import me.juliarn.smartmirror.backend.api.widget.position.WidgetPositionRepository;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSetting;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSettingId;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSettingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@MicronautTest
public class WidgetTest {

  @Inject
  WidgetRegistry widgetRegistry;

  @Inject
  WidgetSettingRepository widgetSettingRepository;

  @Inject
  WidgetPositionRepository widgetPositionRepository;

  @Inject
  AccountRepository accountRepository;

  @Test
  void testWidgetSettingRepository() {
    Account account = this.accountRepository.save(new Account("root", "root", "root")).block();
    Assertions.assertNotNull(account);

    Widget weatherWidget = this.widgetRegistry.get("weather");
    Assertions.assertNotNull(weatherWidget);

    Collection<WidgetSetting> settings = Flux.fromIterable(weatherWidget.defaultSettings())
        .flatMap(defaultSetting ->
            this.widgetSettingRepository.save(
                new WidgetSetting(
                    new WidgetSettingId(account, weatherWidget, defaultSetting.settingName()),
                    defaultSetting.defaultValue())))
        .collectList().block();

    Assertions.assertNotNull(settings);
    Assertions.assertEquals(weatherWidget.defaultSettings().size(), settings.size());

    Collection<WidgetSetting> byAccountSettings = this.widgetSettingRepository.findByIdAccount(
            account)
        .collectList()
        .block();
    Assertions.assertNotNull(byAccountSettings);
    Assertions.assertEquals(Set.copyOf(settings), Set.copyOf(byAccountSettings));

    Collection<WidgetSetting> byAccountWidgetSettings = this.widgetSettingRepository.findByIdAccountAndIdWidget(
            account, weatherWidget)
        .collectList()
        .block();
    Assertions.assertNotNull(byAccountWidgetSettings);
    Assertions.assertEquals(Set.copyOf(settings), Set.copyOf(byAccountWidgetSettings));
  }

  @Test
  void testWidgetPositionRepository() {
    Account account = this.accountRepository.save(new Account("root", "root", "root")).block();
    Assertions.assertNotNull(account);

    Collection<Widget> widgets = this.widgetRegistry.getWidgets();

    Collection<WidgetPosition> widgetPositions = Flux.fromIterable(widgets)
        .flatMap(widget -> this.widgetPositionRepository.save(new WidgetPosition(
            new WidgetPositionId(account, widget),
            PositionArea.TOP_LEFT,
            1F,
            1F)))
        .collectList().block();

    Assertions.assertNotNull(widgetPositions);

    Collection<WidgetPosition> byAccountPositions = this.widgetPositionRepository.findByIdAccount(
            account)
        .collectList().block();
    Assertions.assertNotNull(byAccountPositions);
    Assertions.assertEquals(Set.copyOf(widgetPositions), Set.copyOf(byAccountPositions));

    Collection<WidgetPosition> byAccountWidgetPositions = Flux.fromIterable(widgets)
        .flatMap(widget -> this.widgetPositionRepository.findByIdAccountAndIdWidget(
            account, widget))
        .collectList().block();

    Assertions.assertNotNull(byAccountWidgetPositions);
    Assertions.assertEquals(Set.copyOf(widgetPositions), Set.copyOf(byAccountWidgetPositions));
  }
}
