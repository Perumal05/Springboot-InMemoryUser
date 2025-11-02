package com.zuna.demo.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.zuna.demo.userEntity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
