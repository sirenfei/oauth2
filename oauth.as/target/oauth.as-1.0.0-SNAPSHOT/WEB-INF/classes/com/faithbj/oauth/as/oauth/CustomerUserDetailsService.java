package com.faithbj.oauth.as.oauth;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faithbj.oauth.as.domain.TbUsers;
import com.faithbj.oauth.as.mapper.TbUsersMapper;
import com.google.common.collect.Lists;

/**
 * User: yfxue Date: 12-02-19 Time: 上午07:39
 */
@Service("customerUserDetailsService")
@Transactional
public class CustomerUserDetailsService implements UserDetailsService {

    protected static final Logger logger = LoggerFactory.getLogger(CustomerUserDetailsService.class);

    @Autowired
    @Qualifier("tbUsersMapper")
    private TbUsersMapper tbUsersMapper;

    public TbUsers loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        TbUsers member = tbUsersMapper.selectByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("管理员[" + username + "]不存在!");
        }

        // 解除管理员账户锁定
        // if (member.isAccountNonLocked() == true) {
        // if (systemConfigUtil.getIsLoginFailureLock() == true) {
        // int loginFailureLockTime = systemConfigUtil.getLoginFailureLockTime();
        // if (loginFailureLockTime != 0) {
        // Date lockedDate = member.getLockedDate();
        // Date nonLockedTime = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
        // Date now = new Date();
        // if (now.after(nonLockedTime)) {
        // member.setLoginFailureCount(0);
        // member.setAccountNonLocked(true);
        // member.setLockedDate(null);
        // memberDao.update(member);
        // }
        // }
        // } else {
        // member.setLoginFailureCount(0);
        // member.setAccountNonLocked(true);
        // member.setLockedDate(null);
        // memberDao.update(member);
        // }
        // }
        // }
        member.setAuthorities(getGrantedAuthorities(member));
        return member;
    }

    // 获得管理角色数组
    public List<SimpleGrantedAuthority> getGrantedAuthorities(TbUsers member) {
        List<SimpleGrantedAuthority> grantedAuthorities = Lists.newArrayList();
        List<String> roles = Arrays.asList(member.getRoleName().split(",") ) ;
        for(String role:roles)
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }
}
