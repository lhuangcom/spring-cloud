package com.lhuang.authorization.client.api.dao;

import com.lhuang.authorization.client.api.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lhunag
 * date 2019/7/27
 */
public interface SysUserRepository extends JpaSpecificationExecutor<SysUser>, JpaRepository<SysUser, Integer> {

    SysUser findByUsername(String username);
}