package me.juliarn.smartmirror.backend.impl.services.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.oauth2.grants.AsMap;
import io.micronaut.security.oauth2.grants.GrantType;
import io.micronaut.security.oauth2.grants.SecureGrant;
import io.micronaut.security.oauth2.grants.SecureGrantMap;

import java.util.Map;

@Introspected
@JsonNaming(SnakeCaseStrategy.class)
public class RefreshTokenGrant implements SecureGrant, AsMap {

  private static final String KEY_GRANT_TYPE = "grant_type";
  private static final String KEY_REFRESH_TOKEN = "refresh_token";
  private static final String KEY_CLIENT_ID = "client_id";
  private static final String KEY_CLIENT_SECRET = "client_secret";

  private final String grantType = GrantType.REFRESH_TOKEN.toString();
  private String refreshToken;
  private String clientId;
  private String clientSecret;

  @NonNull
  public String getGrantType() {
    return this.grantType;
  }

  @NonNull
  public String getRefreshToken() {
    return this.refreshToken;
  }

  public void setRefreshToken(@NonNull String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @NonNull
  public String getClientId() {
    return this.clientId;
  }

  @Override
  public void setClientId(@NonNull String clientId) {
    this.clientId = clientId;
  }

  public String getClientSecret() {
    return this.clientSecret;
  }

  @Override
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  @Override
  @NonNull
  public Map<String, String> toMap() {
    Map<String, String> map = new SecureGrantMap(4);

    map.put(KEY_GRANT_TYPE, this.grantType);
    map.put(KEY_REFRESH_TOKEN, this.refreshToken);
    map.put(KEY_CLIENT_ID, this.clientId);

    if (this.clientSecret != null) {
      map.put(KEY_CLIENT_SECRET, this.clientSecret);
    }
    return map;
  }
}
