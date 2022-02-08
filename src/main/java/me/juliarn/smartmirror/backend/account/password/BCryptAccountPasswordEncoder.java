package me.juliarn.smartmirror.backend.account.password;

import jakarta.inject.Singleton;
import javax.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
public class BCryptAccountPasswordEncoder implements AccountPasswordEncoder {

  private final PasswordEncoder delegate = new BCryptPasswordEncoder();

  @Override
  public String encode(@NotBlank String rawPassword) {
    return this.delegate.encode(rawPassword);
  }

  @Override
  public boolean matches(@NotBlank String rawPassword, @NotBlank String encodedPassword) {
    return this.delegate.matches(rawPassword, encodedPassword);
  }
}
