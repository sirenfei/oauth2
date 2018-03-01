package com.faithbj.oauth.as.oauth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

import com.faithbj.oauth.as.domain.OauthClientDetails;
import com.faithbj.oauth.as.service.OauthService;

/**
 * 处理OAuth 中用户授权确认的逻辑
 * 这在grant_type 为 authorization_code, implicit 会使用到
 *
 * @author
 */
//@Service("customUserApprovalHandler")
public class CustomUserApprovalHandler extends TokenStoreUserApprovalHandler {

    @Autowired
    @Qualifier("oauthService")
    private OauthService oauthService;

    public CustomUserApprovalHandler() {
        // Do nothing
    }

    /**
     * 这儿扩展了默认的逻辑, 若 OauthClientDetails 中的 Autoapprove 字段为true, 将会自动跳过 授权流程
     *
     * @param authorizationRequest AuthorizationRequest
     * @param userAuthentication   Authentication
     *
     * @return True is approved
     */
    @Override
    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }
        if (!userAuthentication.isAuthenticated()) {
            return false;
        }
        OauthClientDetails clientDetails = oauthService.findOauthClientDetails(authorizationRequest.getClientId());
        return clientDetails != null && StringUtils.equals("true", clientDetails.getAutoapprove());
    }
}
