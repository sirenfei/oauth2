package com.faithbj.oauth.as.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.faithbj.oauth.as.domain.OauthClientDetails;
import com.faithbj.oauth.as.mapper.OauthClientDetailsMapper;
import com.faithbj.oauth.as.service.OauthService;

@Service("oauthService")
public class OauthServiceImpl implements OauthService {
    protected final Logger logger = LoggerFactory.getLogger(OauthServiceImpl.class);
    
    
    @Autowired
    @Qualifier("oauthClientDetailsMapper")
    private OauthClientDetailsMapper oauthClientDetailsMapper;
    
    @Override
    public OauthClientDetails findOauthClientDetails(String clientId) {
        OauthClientDetails result = oauthClientDetailsMapper.selectByPrimaryKey(clientId);
        return result;
    }

    @Override
    public List<OauthClientDetails> findAllOauthClientDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateOauthClientDetailsByClientId(OauthClientDetails details) {
        int result = oauthClientDetailsMapper.updateByPrimaryKeySelective(details);
        logger.info("updateOauthClientDetailsByClientId done affected:{}", result);
    }

    @Override
    public void saveOauthClientDetails(OauthClientDetails clientDetails) {
        int result = oauthClientDetailsMapper.insertSelective(clientDetails);
        logger.info("saveOauthClientDetails done affected:{}", result);
    }

}
