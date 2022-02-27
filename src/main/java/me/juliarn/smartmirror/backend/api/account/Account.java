package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.util.UUID;

@MappedEntity(value = "account")
public record Account(
    @Id @AutoPopulated @Nullable UUID accountId,
    @NonNull String accountName,
    @NonNull String password,
    @NonNull String firstName) {

  public Account(
      @NonNull String accountName,
      @NonNull String password,
      @NonNull String firstName) {
    this(null, accountName, password, firstName);
  }

  public Account(@Nullable UUID accountId) {
    this(accountId, "", "", "");
  }
}
