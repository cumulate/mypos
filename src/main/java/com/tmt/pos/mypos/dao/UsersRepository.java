package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    public Users findOneByUserName(String userName);
}
