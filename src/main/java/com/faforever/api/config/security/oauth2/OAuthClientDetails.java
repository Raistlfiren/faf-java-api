package com.faforever.api.config.security.oauth2;

import com.faforever.api.client.OAuthClient;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * Wraps an {@link OAuthClient} and maps its fields to Spring's {@link ClientDetails}.
 */
public class OAuthClientDetails extends BaseClientDetails {

  public OAuthClientDetails(OAuthClient oAuthClient) {
    super(oAuthClient.getId(),
        null,
        oAuthClient.getDefaultScope().replace(' ', ','),
        // FIXME read from database instead of hardcoding (but DB migration is required)
        "authorization_code,refresh_token,implicit,password",
        null);
    setClientSecret(oAuthClient.getClientSecret());
  }
}
