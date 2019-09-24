package com.lhuang.authorization.client.service;

import com.lhuang.authorization.client.api.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户认证逻辑：先查询数据库中的用户数据，再进行密码比对校验认证；
 * @author lhunag
 * date 2019/7/27
 */
@Slf4j
@Service
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getByUsername(username);
        if (null == sysUser) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }

        //密码校验逻辑：前端传入密码会自动被passwordEncoder编码加密再与数据库的用户密码比较；
        List list = new ArrayList();
        User sysUserDetails = new User(sysUser.getUsername(), sysUser.getPassword(), list);

        log.info("登录成功！用户: {}", sysUser);
        log.debug("登录成功");

        return sysUserDetails;
    }
}
