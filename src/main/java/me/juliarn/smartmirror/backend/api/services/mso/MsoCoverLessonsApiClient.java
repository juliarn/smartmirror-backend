package me.juliarn.smartmirror.backend.api.services.mso;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;
import java.util.UUID;
import me.juliarn.smartmirror.backend.api.services.mso.model.CoverLessonsState;
import reactor.core.publisher.Mono;

@Header(name = "User-Agent", value = "SmartMirror-Backend")
@Client("https://www.marienschule.com/caldav/cover_lessons")
public interface MsoCoverLessonsApiClient {

  @Get("/{userId}.json")
  Mono<CoverLessonsState> getCoverLessons(@PathVariable UUID userId);
}
