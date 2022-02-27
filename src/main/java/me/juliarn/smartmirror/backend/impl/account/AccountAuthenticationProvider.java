package me.juliarn.smartmirror.backend.impl.account;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.account.AccountRepository;
import me.juliarn.smartmirror.backend.api.account.password.AccountPasswordEncoder;
import org.reactivestreams.Publisher;

@Singleton
public class AccountAuthenticationProvider implements AuthenticationProvider {

  private final AccountRepository accountRepository;
  private final AccountPasswordEncoder passwordEncoder;

  @Inject
  public AccountAuthenticationProvider(
      AccountRepository accountRepository,
      AccountPasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest) {
    return this.accountRepository.findByAccountNameIlike(
            authenticationRequest.getIdentity().toString()).flux().map(account -> {
          if (this.passwordEncoder.matches(
              authenticationRequest.getSecret().toString(),
              account.password())) {
            return AuthenticationResponse.success(
                String.valueOf(account.accountId()),
                List.of(Roles.ACCOUNT));
          } else {
            return AuthenticationResponse.failure("Invalid credentials");
          }
        })
        .defaultIfEmpty(AuthenticationResponse.failure("Account does not exist"));
  }
}
