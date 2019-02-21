package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, String> {
    public User findOneByUserName(String userName);
}
