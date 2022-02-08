package me.juliarn.smartmirror.backend.account;

import org.hibernate.annotations.GenericGenerator;
import reactor.util.annotation.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator")
  private UUID accountId;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String firstName;

  Account(@NotBlank String username, @NotBlank String password, @NotBlank String firstName) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
  }

  public Account() {
  }

  public @NotNull UUID getAccountId() {
    return this.accountId;
  }

  public void setAccountId(@NotNull UUID accountId) {
    this.accountId = accountId;
  }

  public @NotBlank String getUsername() {
    return this.username;
  }

  public void setUsername(@NotBlank String username) {
    this.username = username;
  }

  public @NotBlank String getPassword() {
    return this.password;
  }

  public void setPassword(@NotBlank String password) {
    this.password = password;
  }

  public @NotBlank String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(@NotBlank String firstName) {
    this.firstName = firstName;
  }
}
