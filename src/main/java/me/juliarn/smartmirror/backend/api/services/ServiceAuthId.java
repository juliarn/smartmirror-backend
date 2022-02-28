package me.juliarn.smartmirror.backend.api.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.Relation.Kind;
import me.juliarn.smartmirror.backend.api.account.Account;

@Embeddable
public record ServiceAuthId(
    @Relation(value = Kind.MANY_TO_ONE) @NonNull Account account,
    @NonNull String serviceName) {

}
