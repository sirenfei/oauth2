package com.faithbj.oauth.as.domain;

import java.io.Serializable;

public class OauthAccessToken implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_access_token.authentication_id
     *
     * @mbg.generated
     */
    private String authenticationId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_access_token.token_id
     *
     * @mbg.generated
     */
    private String tokenId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_access_token.user_name
     *
     * @mbg.generated
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_access_token.client_id
     *
     * @mbg.generated
     */
    private String clientId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_access_token.refresh_token
     *
     * @mbg.generated
     */
    private String refreshToken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table oauth_access_token
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_access_token.authentication_id
     *
     * @return the value of oauth_access_token.authentication_id
     *
     * @mbg.generated
     */
    public String getAuthenticationId() {
        return authenticationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_access_token.authentication_id
     *
     * @param authenticationId the value for oauth_access_token.authentication_id
     *
     * @mbg.generated
     */
    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId == null ? null : authenticationId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_access_token.token_id
     *
     * @return the value of oauth_access_token.token_id
     *
     * @mbg.generated
     */
    public String getTokenId() {
        return tokenId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_access_token.token_id
     *
     * @param tokenId the value for oauth_access_token.token_id
     *
     * @mbg.generated
     */
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId == null ? null : tokenId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_access_token.user_name
     *
     * @return the value of oauth_access_token.user_name
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_access_token.user_name
     *
     * @param userName the value for oauth_access_token.user_name
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_access_token.client_id
     *
     * @return the value of oauth_access_token.client_id
     *
     * @mbg.generated
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_access_token.client_id
     *
     * @param clientId the value for oauth_access_token.client_id
     *
     * @mbg.generated
     */
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_access_token.refresh_token
     *
     * @return the value of oauth_access_token.refresh_token
     *
     * @mbg.generated
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_access_token.refresh_token
     *
     * @param refreshToken the value for oauth_access_token.refresh_token
     *
     * @mbg.generated
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }
}