package com.faithbj.oauth.as.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.faithbj.oauth.as.utils.Jackson2Mapper;
import com.google.common.collect.Maps;

public class OauthClientDetail implements ClientDetails {
    
    private static final long serialVersionUID = 748734764244468144L;
    protected final Logger logger = LoggerFactory.getLogger(OauthClientDetails.class);
    
    private String clientId;
    private String resourceId;
    private String clientSecret;
    private String scopes;
    private String authorizedGrantType;
    private String webServerRedirectUris;
    private String authoritie;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;
    
    
    public String getAuthoritie() {
        return authoritie;
    }
    public void setAuthoritie(String authoritie) {
        this.authoritie = authoritie;
    }
    public String getWebServerRedirectUris() {
        return webServerRedirectUris;
    }
    public void setWebServerRedirectUris(String webServerRedirectUris) {
        this.webServerRedirectUris = webServerRedirectUris;
    }
    public String getAuthorizedGrantType() {
        return authorizedGrantType;
    }
    public void setAuthorizedGrantType(String authorizedGrantType) {
        this.authorizedGrantType = authorizedGrantType;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public Set<String> getResourceIds() {
        if(resourceId != null)
        {
            return new HashSet<String>(Arrays.asList(resourceId.split(",")));
        }
        return null;
    }
    
    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public boolean isScoped() {
        return true;
        //TODO
//        return StringUtils.isNotBlank(scope);
    }
    @Override
    public Set<String> getScope() {
        if(scopes != null)
        {
            return new HashSet<String>(Arrays.asList(scopes.split(",")));
        }
        return null;
    }
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if(authorizedGrantType != null)
        {
            return new HashSet<String>(Arrays.asList(authorizedGrantType.split(",")));
        }
        return null;
    }
    @Override
    public Set<String> getRegisteredRedirectUri() {
        if(webServerRedirectUris != null)
        {
            return new HashSet<String>(Arrays.asList(webServerRedirectUris.split(",")));
        }
        return null;
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if(authoritie != null)
        {
            Collection<GrantedAuthority> grantedAuthorities= new HashSet<GrantedAuthority>();
            String [] auths = authoritie.split(",");
            for(String role:auths)
            {
                GrantedAuthority ga = new SimpleGrantedAuthority(role);
                grantedAuthorities.add(ga);
            }
            return grantedAuthorities;
        }
        return null;
    }
    @Override
    public Integer getAccessTokenValiditySeconds() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean isAutoApprove(String scope) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public Map<String, Object> getAdditionalInformation() {
        if (additionalInformation != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> adinfo = Jackson2Mapper.read(additionalInformation, Map.class);
                return adinfo;
            }
            catch (Exception e) {
                logger.error("Could not decode JSON for additional information: " + additionalInformation, e);
            }
        }
        return Maps.newHashMap();
    }
    
    

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }
    public void setResourceIds(String resourceIds) {
        this.resourceId = resourceIds == null ? null : resourceIds.trim();
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret == null ? null : clientSecret.trim();
    }
    public void setScope(String scope) {
        this.scopes = scope == null ? null : scope.trim();
    }
    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantType = authorizedGrantTypes == null ? null : authorizedGrantTypes.trim();
    }
    
    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUris = webServerRedirectUri == null ? null : webServerRedirectUri.trim();
    }
    public void setAuthorities(String authorities) {
        this.authoritie = authorities == null ? null : authorities.trim();
    }
    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }
    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }
    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }
    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation == null ? null : additionalInformation.trim();
    }
    public String getAutoapprove() {
        return autoapprove;
    }
    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove == null ? null : autoapprove.trim();
    }
  
}