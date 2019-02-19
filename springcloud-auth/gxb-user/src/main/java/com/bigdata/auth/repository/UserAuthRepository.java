package com.bigdata.auth.repository;

import com.bigdata.auth.domain.TUserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户认证
 *
 * @author fwj
 * @date 2019-02-12 16:17
 **/
public interface UserAuthRepository extends JpaRepository<TUserAuthEntity, Integer> {
    Optional<TUserAuthEntity> findByIdentifier(String identifier);
}
