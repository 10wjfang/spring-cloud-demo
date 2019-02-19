package com.bigdata.auth.service;

import com.bigdata.auth.domain.TRoleEntity;
import com.bigdata.auth.domain.TUserAuthEntity;
import com.bigdata.auth.repository.RoleRepository;
import com.bigdata.auth.repository.UserAuthRepository;
import com.bigdata.auth.repository.UserRoleRepository;
import com.bigdata.auth.domain.RUserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义用户信息实现类
 *
 * @author fwj
 * @date 2019-02-12 22:19
 **/
@Component
public class CustomUserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserService.class);
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.debug("username: {}", s);
        TUserAuthEntity userAuthEntity = userAuthRepository.findByIdentifier(s).orElse(null);
        if (userAuthEntity == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<TRoleEntity> roleEntityList = new ArrayList<>();
        List<RUserRoleEntity> userRoleEntityList = userRoleRepository.findByUid(userAuthEntity.getUid());
        for (RUserRoleEntity entity : userRoleEntityList) {
            TRoleEntity roleEntity = roleRepository.findById(entity.getRoleId()).orElse(null);
            if (roleEntity != null) {
                roleEntityList.add(roleEntity);
            }
        }
        Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
        for (TRoleEntity entity : roleEntityList) {
            simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(entity.getName()));
        }
        return new User(userAuthEntity.getIdentifier(), userAuthEntity.getCredential(), simpleGrantedAuthoritySet);
    }
}
