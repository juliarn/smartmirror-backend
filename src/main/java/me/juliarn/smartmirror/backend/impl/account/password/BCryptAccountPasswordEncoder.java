package me.juliarn.smartmirror.backend.account.password;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.validation.constraints.NotBlank;

import me.juliarn.smartmirror.backend.api.account.password.AccountPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
public class BCryptAccountPasswordEncoder implements AccountPasswordEncoder {

  private final PasswordEncoder delegate;

  @Inject
  BCryptAccountPasswordEncoder() {
    this.delegate = new BCryptPasswordEncoder();
  }

  @Override
  public String encode(@NotBlank String rawPassword) {
    return this.delegate.encode(rawPassword);
  }

  @Override
  public boolean matches(@NotBlank String rawPassword, @NotBlank String encodedPassword) {
    return this.delegate.matches(rawPassword, encodedPassword);
  }
}
