package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.security.authentication.Authentication;

@MappedEntity(value = "account")
public record Account(
    @Id @GeneratedValue @AutoPopulated @Nullable Long accountId,
    @NonNull String accountName,
    @NonNull String password,
    @NonNull String firstName) {

  public Account(
      @NonNull String accountName,
      @NonNull String password,
      @NonNull String firstName) {
    this(null, accountName, password, firstName);
  }

  public Account(@NonNull Long accountId) {
    this(accountId, "", "", "");
  }

  public static Account fromAuthentication(@NonNull Authentication authentication) {
    Long accountId = Long.parseLong(authentication.getName());
    return new Account(accountId);
  }
}
