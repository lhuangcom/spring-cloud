package com.lhuang.authorization.client.service;

import com.lhuang.authorization.client.api.model.SysUser;

/**
 * @author lhunag
 * date 2019/7/27
 */
public interface UserService {

    SysUser getByUsername(String username);
}
