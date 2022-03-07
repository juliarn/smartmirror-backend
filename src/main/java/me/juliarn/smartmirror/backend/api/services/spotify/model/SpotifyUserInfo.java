package me.juliarn.smartmirror.backend.api.services.spotify.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
public final class SpotifyUserInfo {

  private String displayName;
  private String id;
  private String product;

  @Creator
  public SpotifyUserInfo(String displayName, String id, String product) {
    this.displayName = displayName;
    this.id = id;
    this.product = product;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProduct() {
    return this.product;
  }

  public void setProduct(String product) {
    this.product = product;
  }
}
