package me.juliarn.smartmirror.backend.account.password;

import javax.validation.constraints.NotBlank;

public interface AccountPasswordEncoder {

  String encode(@NotBlank String rawPassword);

  boolean matches(@NotBlank String rawPassword, @NotBlank String encodedPassword);
}
