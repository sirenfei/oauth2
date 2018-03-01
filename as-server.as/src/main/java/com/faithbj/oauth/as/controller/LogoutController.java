package com.faithbj.oauth.as.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author xueyongfei01
 *
 */
@Controller
public class LogoutController {
    protected final Logger logger = LoggerFactory.getLogger(LogoutController.class);


    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
	private TokenStore tokenStore;
    
    /**
     * 根据clientId和username获取该用户的token信息
     * @param client
     * @param user
     * @param principal
     * @return
     * @throws Exception
     */
	@RequestMapping("/clientToken/{client}/{user}/tokens")
	@ResponseBody
	public Collection<OAuth2AccessToken> listTokensForUser(@PathVariable("client") String client, @PathVariable("user") String user,
			Principal principal) throws Exception {
		checkResourceOwner(user, principal);
		return enhance(tokenStore.findTokensByClientIdAndUserName(client, user));
	}

	/**
	 * 退出登陆，同时删除token
	 * @param clientId
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/logout/{clientId}", method = RequestMethod.GET)
	public String logout(@PathVariable("clientId") String clientId, Principal principal)
	{
		if(principal !=null && principal.getName()!=null)
		{
			String username = principal.getName();
			Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName( clientId, username);
			for (OAuth2AccessToken prototype : tokens) {
				tokenServices.revokeToken(prototype.getValue());
			}
		}
		return "redirect:/logout";
	}
	
	
	
	@RequestMapping(value = "/logout/{clientId}/{username}", method = RequestMethod.GET)
	@ResponseBody
	public String logoutA(@PathVariable("clientId") String clientId, @PathVariable("username") String username)
	{
		if(username !=null && clientId!=null)
		{
			Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName( clientId, username);
			for (OAuth2AccessToken prototype : tokens) {
				tokenServices.revokeToken(prototype.getValue());
			}
		}
		return "redirect:/logout";
	}
	
	/**
	 * 列出该client的所有token
	 * @param client
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/oauth/clients/{client}/tokens")
	@ResponseBody
	public Collection<OAuth2AccessToken> listTokensForClient(@PathVariable String client) throws Exception {
		return enhance(tokenStore.findTokensByClientId(client));
	}

	
	
	
	@RequestMapping(value = "/deletetoken/{user}/{token}/token", method = RequestMethod.GET)
	@ResponseBody
	public String revokeToken(@PathVariable String user, @PathVariable String token, Principal principal)
			throws Exception {
		checkResourceOwner(user, principal);
		if (tokenServices.revokeToken(token)) {
			return "OK";
		}
		else {
			return "NOT_FOUND";
		}
	}
	
	@RequestMapping(value = "/deleteAll/{userName}/{clientId}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteAll(@PathVariable String userName, @PathVariable String clientId, Principal principal)
			throws Exception {
		Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, userName);
		for (OAuth2AccessToken prototype : tokens) {
			tokenServices.revokeToken(prototype.getValue());
		}
		
		return "OK";
	}

	private void checkResourceOwner(String user, Principal principal) {
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication authentication = (OAuth2Authentication) principal;
			if (!authentication.isClientOnly() && !user.equals(principal.getName())) {
				throw new AccessDeniedException(String.format("User '%s' cannot obtain tokens for user '%s'",
						principal.getName(), user));
			}
		}
	}
	
	private Collection<OAuth2AccessToken> enhance(Collection<OAuth2AccessToken> tokens) {
		Collection<OAuth2AccessToken> result = new ArrayList<OAuth2AccessToken>();
		for (OAuth2AccessToken prototype : tokens) {
			DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(prototype);
			OAuth2Authentication authentication = tokenStore.readAuthentication(token);
			if (authentication == null) {
				continue;
			}
			String clientId = authentication.getOAuth2Request().getClientId();
			if (clientId != null) {
				Map<String, Object> map = new HashMap<String, Object>(token.getAdditionalInformation());
				map.put("client_id", clientId);
				token.setAdditionalInformation(map);
				result.add(token);
			}
		}
		return result;
	}

}
