package com.faithbj.oauth.as;

import static org.junit.Assert.*;

import org.apache.http.HttpHeaders;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ImplicitAccessTokenProviderTests {
	@Rule
	public ExpectedException expected = ExpectedException.none();

	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

	private ImplicitAccessTokenProvider provider = new ImplicitAccessTokenProvider() {
		protected OAuth2AccessToken retrieveToken(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource,
				MultiValueMap<String, String> form, HttpHeaders headers) {
			params.putAll(form);
			return new DefaultOAuth2AccessToken("FOO");
		}
	};

	private ImplicitResourceDetails resource = new ImplicitResourceDetails();

	@Test(expected = IllegalStateException.class)
	public void testRedirectNotSpecified() throws Exception {
		AccessTokenRequest request = new DefaultAccessTokenRequest();
		provider.obtainAccessToken(resource, request);
	}

	@Test
	public void testGetAccessTokenRequest() throws Exception {
		AccessTokenRequest request = new DefaultAccessTokenRequest();
		resource.setClientId("123456");
		resource.setAccessTokenUri("http://localhost:8888/oauth/authorize");
		resource.setPreEstablishedRedirectUri("http://baidu.com");
		assertEquals("123456", provider.obtainAccessToken(resource, request).getValue());
		assertEquals("123456", params.getFirst("client_id"));
		assertEquals("token", params.getFirst("response_type"));
		assertEquals("http://baidu.com", params.getFirst("redirect_uri"));
}
}
