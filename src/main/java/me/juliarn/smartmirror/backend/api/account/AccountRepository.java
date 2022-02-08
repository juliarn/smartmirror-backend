package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotBlank;

@JdbcRepository
public interface AccountRepository extends CrudRepository<Account, UUID> {

  Optional<Account> findByAccountName(@NotBlank String accountName);
}
