package me.juliarn.smartmirror.backend.api.account;

import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import java.util.UUID;

@MappedEntity(value = "account")
public class Account {

  @Id
  @AutoPopulated
  private UUID accountId;
  private String accountName;
  private String password;
  private String firstName;

  public Account(String accountName, String password, String firstName) {
    this.accountName = accountName;
    this.password = password;
    this.firstName = firstName;
  }

  public Account() {
  }

  public UUID getAccountId() {
    return this.accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public String getAccountName() {
    return this.accountName;
  }

  public void setAccountName(String username) {
    this.accountName = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}
