package me.juliarn.smartmirror.backend.impl.account;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import javax.validation.constraints.NotBlank;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.account.AccountRepository;
import me.juliarn.smartmirror.backend.api.account.password.AccountPasswordEncoder;
import reactor.core.publisher.Mono;

@Controller("/api/account")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AccountController {

  private final AccountRepository accountRepository;
  private final AccountPasswordEncoder passwordEncoder;

  @Inject
  AccountController(
      AccountRepository accountRepository,
      AccountPasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Post("/register")
  Mono<MutableHttpResponse<String>> register(
      @NotBlank String username,
      @NotBlank String password,
      @NotBlank String firstName) {
    return this.accountRepository.findByAccountNameIlike(username)
        .map(account -> HttpResponse.badRequest("Account already exists"))
        .switchIfEmpty(this.accountRepository.save(
                new Account(username, this.passwordEncoder.encode(password), firstName))
            .map(account -> HttpResponse.ok()));
  }
}
