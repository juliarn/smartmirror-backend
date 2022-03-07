package me.juliarn.smartmirror.backend.impl.account;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import me.juliarn.smartmirror.backend.api.account.Account;
import me.juliarn.smartmirror.backend.api.account.AccountRepository;
import me.juliarn.smartmirror.backend.api.account.password.AccountPasswordEncoder;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.UUID;

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
  Mono<MutableHttpResponse<JsonError>> register(
      @NotBlank String username,
      @NotBlank String password,
      @NotBlank String firstName) {
    return this.accountRepository.findByAccountNameIlike(username)
        .map(account -> HttpResponse.badRequest(new JsonError("Account name is already taken")))
        .switchIfEmpty(this.accountRepository.save(
                new Account(username, this.passwordEncoder.encode(password), firstName))
            .map(account -> HttpResponse.ok()));
  }

  @Get("/info")
  @Secured(SecurityRule.IS_AUTHENTICATED)
  Mono<Map<String, String>> getInfo(@NonNull Authentication authentication) {
    UUID accountId = UUID.fromString(authentication.getName());

    return this.accountRepository.findById(accountId).map(
        account -> Map.of("username", account.accountName(), "firstName", account.firstName()));
  }
}
