package com.dubai.oauth.resource.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dubai.oauth.resource.domain.TbUsers;
import com.dubai.oauth.resource.domain.TbUsersCriteria;

//@Repository("tbUsersMapper")
public interface TbUsersMapper {
    long countByExample(TbUsersCriteria example);
    int deleteByExample(TbUsersCriteria example);
    int deleteByPrimaryKey(Integer id);
    int insert(TbUsers record);
    int insertSelective(TbUsers record);
    List<TbUsers> selectByExample(TbUsersCriteria example);
    TbUsers selectByPrimaryKey(Integer id);
    int updateByExampleSelective(@Param("record") TbUsers record, @Param("example") TbUsersCriteria example);
    int updateByExample(@Param("record") TbUsers record, @Param("example") TbUsersCriteria example);
    int updateByPrimaryKeySelective(TbUsers record);
    int updateByPrimaryKey(TbUsers record);
    
    @Select("select id,username,password,email,mobile_phone as mobilePhone,role_name as roleName,enabled,account_nonexpired as accountNonexpired,account_nonlocked as accountNonlocked,credentials_nonexpired as credentialsNonexpired from tb_users where username= #{username} limit 1")
    TbUsers selectByUsername(@Param("username") String username);
}