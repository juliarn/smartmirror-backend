package me.juliarn.smartmirror.backend.account;

import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AccountRepository {

  Optional<Account> findByAccountId(@NotNull UUID accountId);

  Optional<Account> findByAccountName(@NotBlank String accountName);

  Account save(@NotBlank String accountName, @NotBlank String password, @NotBlank String firstName);
}
