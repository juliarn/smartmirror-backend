package me.juliarn.smartmirror.backend.api.services.auth;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity("service_auth")
public record ServiceAuth(
    @EmbeddedId @NonNull ServiceAuthId id,
    @NonNull String refreshToken) {

}
