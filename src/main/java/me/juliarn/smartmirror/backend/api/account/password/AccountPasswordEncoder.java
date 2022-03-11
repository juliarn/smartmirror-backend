package me.juliarn.smartmirror.backend.api.account.password;

import javax.validation.constraints.NotBlank;

/**
 * Encodes passwords with a certain hash algorithm.
 */
public interface AccountPasswordEncoder {

  /**
   * Hashes a password.
   *
   * @param rawPassword The password to hash
   * @return The hashed password
   */
  String encode(@NotBlank String rawPassword);

  /**
   * Checks whether a certain password matches with a certain hash of a password.
   *
   * @param rawPassword     The raw password in clear text
   * @param encodedPassword The hashed password
   * @return Whether the hash and password match
   */
  boolean matches(@NotBlank String rawPassword, @NotBlank String encodedPassword);
}
