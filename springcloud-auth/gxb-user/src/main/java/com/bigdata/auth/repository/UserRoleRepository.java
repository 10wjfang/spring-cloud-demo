package com.bigdata.auth.repository;

import com.bigdata.auth.domain.RUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户关联角色
 *
 * @author fwj
 * @date 2019-02-12 22:31
 **/
public interface UserRoleRepository extends JpaRepository<RUserRoleEntity, Integer> {
    List<RUserRoleEntity> findByUid(Integer uId);
}
