package com.lhuang.authorization.client.service.impl;

import com.lhuang.authorization.client.api.dao.SysUserRepository;
import com.lhuang.authorization.client.api.model.SysUser;
import com.lhuang.authorization.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lhunag
 * date 2019/7/27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserRepository.findByUsername(username);
    }
}
