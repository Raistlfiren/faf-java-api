package com.faforever.api.config.security.oauth2;

import com.faforever.api.client.OAuthClientRepository;
import com.faforever.api.config.FafApiProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import javax.inject.Inject;

public class OAuthClientDetailsService implements ClientDetailsService {

  public static final String CLIENTS_CACHE_NAME = "oAuthClients";
  private final OAuthClientRepository oAuthClientRepository;
  private final FafApiProperties fafApiProperties;

  @Inject
  public OAuthClientDetailsService(OAuthClientRepository oAuthClientRepository, FafApiProperties fafApiProperties) {
    this.oAuthClientRepository = oAuthClientRepository;
    this.fafApiProperties = fafApiProperties;
  }

  @Override
  @Cacheable(CLIENTS_CACHE_NAME)
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    OAuthClientDetails clientDetails = new OAuthClientDetails(oAuthClientRepository.findOne(clientId));
    clientDetails.setAccessTokenValiditySeconds(fafApiProperties.getJwt().getAccessTokenValiditySeconds());
    clientDetails.setRefreshTokenValiditySeconds(fafApiProperties.getJwt().getRefreshTokenValiditySeconds());
    return clientDetails;
  }
}
