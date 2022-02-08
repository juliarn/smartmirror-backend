package me.juliarn.smartmirror.backend.account;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Singleton
public class DefaultAccountRepository implements AccountRepository {

  private final EntityManager entityManager;

  @Inject
  public DefaultAccountRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public Optional<Account> findByAccountId(@NotNull UUID accountId) {
    return Optional.ofNullable(this.entityManager.find(Account.class, accountId));
  }

  @Override
  @Transactional
  public Optional<Account> findByAccountName(@NotBlank String accountName) {
    try {
      return Optional.of((Account) this.entityManager.createQuery(
              "SELECT FROM Account a WHERE a.accountName = :accountName")
          .setParameter("accountName", accountName)
          .getSingleResult());
    } catch (NoResultException exception) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Account save(
      @NotBlank String accountName,
      @NotBlank String password,
      @NotBlank String firstName) {
    Account account = new Account(accountName, password, firstName);
    this.entityManager.persist(account);
    return account;
  }
}
