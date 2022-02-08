package me.juliarn.smartmirror.backend.account;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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

  public Account(String username, String password, String firstName) {
    this.username = username;
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

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
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
