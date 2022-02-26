package me.juliarn.smartmirror.backend.account;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.account.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

@MicronautTest
public class AccountRepositoryTest {

  @Inject
  AccountRepository accountRepository;

  @Test
  public void testAccountRepository() {
    Long countBefore = Mono.from(this.accountRepository.count()).block();

    Account account = this.accountRepository.save(new Account("root", "root", "root")).block();
    Assertions.assertNotNull(account);
    Assertions.assertNotNull(account.getAccountId());

    Account accountByName = this.accountRepository.findByAccountName("root").block();
    Assertions.assertNotNull(accountByName);

    Account wrongAccountByName = this.accountRepository.findByAccountName("admin").block();
    Assertions.assertNull(wrongAccountByName);

    Account accountById = this.accountRepository.findById(account.getAccountId()).block();
    Assertions.assertNotNull(accountById);

    this.accountRepository.delete(account).block();

    Long count = Mono.from(this.accountRepository.count()).block();
    Assertions.assertEquals(countBefore, count);
  }
}
