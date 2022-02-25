package me.juliarn.smartmirror.backend.impl.account;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import me.juliarn.smartmirror.backend.api.Roles;
import me.juliarn.smartmirror.backend.api.account.AccountRepository;
import me.juliarn.smartmirror.backend.api.account.password.AccountPasswordEncoder;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

@Singleton
public record AccountAuthenticationProvider(
    AccountRepository accountRepository,
    AccountPasswordEncoder passwordEncoder) implements AuthenticationProvider {

  @Inject
  public AccountAuthenticationProvider {
  }

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest) {
    return this.accountRepository.findByAccountName(authenticationRequest.getIdentity().toString())
        .handle((account, sink) -> {
          if (this.passwordEncoder.matches(
              authenticationRequest.getSecret().toString(),
              account.getPassword())) {
            sink.next(AuthenticationResponse.success(
                account.getAccountName(),
                List.of(Roles.ACCOUNT),
                Map.of("account", account)));
          } else {
            sink.error(AuthenticationResponse.exception("Invalid credentials"));
          }
        });
  }
}
