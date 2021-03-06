package com.faforever.api.config.security.oauth2;

import com.faforever.api.user.FafUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

/**
 * Converts a {@link FafUserDetails} from and to an {@link Authentication} for use in a JWT token.
 */
public class FafUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

  private static final String ID = "user_id";
  private static final String NON_LOCKED = "nonLocked";

  @Override
  public Map<String, ?> convertUserAuthentication(Authentication authentication) {
    UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
    FafUserDetails fafUserDetails = (FafUserDetails) authenticationToken.getPrincipal();

    @SuppressWarnings("unchecked")
    Map<String, Object> response = (Map<String, Object>) super.convertUserAuthentication(authentication);
    response.put(ID, fafUserDetails.getId());
    response.put(NON_LOCKED, fafUserDetails.isAccountNonLocked());

    return response;
  }

  @Override
  public Authentication extractAuthentication(Map<String, ?> map) {
    int id = (Integer) map.get(ID);
    String username = (String) map.get(USERNAME);
    boolean accountNonLocked = (Boolean) map.get(NON_LOCKED);
    Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
    UserDetails user = new FafUserDetails(id, username, "N/A", accountNonLocked, authorities);

    return new UsernamePasswordAuthenticationToken(user, "N/A", authorities);
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
    if (!map.containsKey(AUTHORITIES)) {
      return Collections.emptySet();
    }
    Object authorities = map.get(AUTHORITIES);
    if (authorities instanceof String) {
      return commaSeparatedStringToAuthorityList((String) authorities);
    }
    if (authorities instanceof Collection) {
      return commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
    }
    throw new IllegalArgumentException("Authorities must be either a String or a Collection");
  }
}
