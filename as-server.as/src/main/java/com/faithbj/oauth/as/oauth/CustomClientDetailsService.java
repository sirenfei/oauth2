package com.faithbj.oauth.as.oauth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faithbj.oauth.as.domain.OauthClientDetail;
import com.faithbj.oauth.as.domain.OauthClientDetails;
import com.faithbj.oauth.as.domain.OauthClientDetailsCriteria;
import com.faithbj.oauth.as.mapper.OauthClientDetailsMapper;

@Service("customClientDetailsService")
@Transactional
public class CustomClientDetailsService implements ClientDetailsService{
    protected static final Logger logger = LoggerFactory.getLogger(CustomerUserDetailsService.class);
    
    @Autowired
    @Qualifier("oauthClientDetailsMapper")
    private OauthClientDetailsMapper oauthClientDetailsMapper;
    
    
    public void addClientDetails(OauthClientDetails clientDetails) throws ClientAlreadyExistsException {
        int result = oauthClientDetailsMapper.insertSelective(clientDetails);
        logger.info("addClientDetails done,result:{}", result);
    }

    public void updateClientDetails(OauthClientDetails clientDetails) throws NoSuchClientException {
        int result = oauthClientDetailsMapper.updateByPrimaryKeySelective(clientDetails);
        logger.info("updateClientDetails done,result:{}", result);
    }

    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        OauthClientDetails clientDetails = new OauthClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(secret);
        
        int result = oauthClientDetailsMapper.updateByPrimaryKeySelective(clientDetails);
        logger.info("updateClientSecret done,result:{}", result);
    }

    public void removeClientDetails(String clientId) throws NoSuchClientException {
        int result = oauthClientDetailsMapper.deleteByPrimaryKey(clientId);
        logger.info("removeClientDetails done,result:{}", result);
    }
    
    public List<OauthClientDetails> listClientDetails() {
        OauthClientDetailsCriteria example = new OauthClientDetailsCriteria();
        example.setOrderByClause("desc");
        List<OauthClientDetails> result = oauthClientDetailsMapper.selectByExample(example);
        return result;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetail result = oauthClientDetailsMapper.selectByClientId(clientId);
        return result;
    }

}
