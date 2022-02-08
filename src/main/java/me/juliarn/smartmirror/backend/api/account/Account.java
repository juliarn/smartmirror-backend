package me.juliarn.smartmirror.backend.api.account;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "account")
public class Account {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator")
  private UUID accountId;

  @Column(nullable = false)
  private String accountName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
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
