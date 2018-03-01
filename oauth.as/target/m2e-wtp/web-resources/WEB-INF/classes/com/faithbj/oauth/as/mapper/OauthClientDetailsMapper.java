package com.faithbj.oauth.as.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.faithbj.oauth.as.domain.OauthClientDetail;
import com.faithbj.oauth.as.domain.OauthClientDetails;
import com.faithbj.oauth.as.domain.OauthClientDetailsCriteria;

public interface OauthClientDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    long countByExample(OauthClientDetailsCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int deleteByExample(OauthClientDetailsCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String clientId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int insert(OauthClientDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int insertSelective(OauthClientDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    List<OauthClientDetails> selectByExample(OauthClientDetailsCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    OauthClientDetails selectByPrimaryKey(String clientId);
    
    OauthClientDetail selectByClientId(String clientId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") OauthClientDetails record, @Param("example") OauthClientDetailsCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") OauthClientDetails record, @Param("example") OauthClientDetailsCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(OauthClientDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_client_details
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OauthClientDetails record);
}