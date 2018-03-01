package com.faithbj.oauth.as.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class TbUsers implements Serializable, UserDetails {
    private static final long serialVersionUID = -3216318042797462093L;
    
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String mobilePhone;
    private Boolean accountNonexpired = true;
    private Boolean accountNonlocked;
    private Boolean credentialsNonexpired;
    private Boolean enabled;
    private String roleName;
    private Short roleId;
    private Date lockDate;
    private Integer loginFailureCount;
    private Date lastLoginTime;
    private Date createDate;
    private Date updateDate;
    
    private List<SimpleGrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


    public void setAccountNonexpired(Boolean accountNonexpired) {
        this.accountNonexpired = accountNonexpired;
    }


    public void setAccountNonlocked(Boolean accountNonlocked) {
        this.accountNonlocked = accountNonlocked;
    }


    public void setCredentialsNonexpired(Boolean credentialsNonexpired) {
        this.credentialsNonexpired = credentialsNonexpired;
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonexpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonexpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
    
   
}