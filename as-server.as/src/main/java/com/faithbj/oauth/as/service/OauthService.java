package com.faithbj.oauth.as.service;

import java.util.List;

import com.faithbj.oauth.as.domain.OauthClientDetails;

public interface OauthService {
    
    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsByClientId(OauthClientDetails details);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}
