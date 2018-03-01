package com.faithbj.oauth.as.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.faithbj.oauth.as.domain.TbUsers;

/**
 * 增加的额外信息
 * @author xueyongfei01
 *
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        TbUsers user = (TbUsers) authentication.getUserAuthentication().getPrincipal();
        // TbUsers user = (TbUsers) authentication.getPrincipal();
        additionalInfo.put("username", authentication.getName());
        if (user != null) {
            additionalInfo.put("email", user.getEmail());
            additionalInfo.put("mobilePhone", user.getMobilePhone());
            additionalInfo.put("authorities", user.getAuthorities());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }
        return accessToken;
    }
}