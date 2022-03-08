package me.juliarn.smartmirror.backend.impl.services.mso;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import java.util.UUID;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.services.mso.MsoCoverLessonsApiClient;
import me.juliarn.smartmirror.backend.api.services.mso.model.CoverLessonsState;
import me.juliarn.smartmirror.backend.api.services.mso.model.CoverLessonsState.CoverLesson;
import me.juliarn.smartmirror.backend.api.widget.Widget;
import me.juliarn.smartmirror.backend.api.widget.WidgetRegistry;
import me.juliarn.smartmirror.backend.api.widget.setting.WidgetSettingRepository;
import reactor.core.publisher.Mono;

@Controller("api/services/mso")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MsoController {

  private static final CoverLessonsState DEFAULT_COVER_LESSONS_STATE = new CoverLessonsState(
      new CoverLesson[0]);

  private static final String MSO_WIDGET_NAME = "mso";
  private static final String USER_ID_SETTING_NAME = "userId";

  private final Widget msoWidget;
  private final WidgetSettingRepository widgetSettingRepository;
  private final MsoCoverLessonsApiClient msoCoverLessonsApiClient;

  @Inject
  public MsoController(
      WidgetRegistry widgetRegistry,
      WidgetSettingRepository widgetSettingRepository,
      MsoCoverLessonsApiClient msoCoverLessonsApiClient) {
    this.msoWidget = widgetRegistry.get(MSO_WIDGET_NAME);
    this.widgetSettingRepository = widgetSettingRepository;
    this.msoCoverLessonsApiClient = msoCoverLessonsApiClient;
  }

  @Get("/state")
  Mono<CoverLessonsState> getCoverLessonsState(@NonNull Authentication authentication) {
    return Mono.from(this.widgetSettingRepository.findByIdAccountAndIdWidget(
                Account.fromAuthentication(authentication), this.msoWidget)
            .filter(widgetSetting -> USER_ID_SETTING_NAME.equals(widgetSetting.id().settingName())))
        .flatMap(widgetSetting -> {
          try {
            UUID userId = UUID.fromString(widgetSetting.value());
            return this.msoCoverLessonsApiClient.getCoverLessons(userId)
                .onErrorReturn(DEFAULT_COVER_LESSONS_STATE);
          } catch (IllegalArgumentException exception) {
            return Mono.just(DEFAULT_COVER_LESSONS_STATE);
          }
        });
  }
}
