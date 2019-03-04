package com.tmt.pos.mypos.dao;

import com.tmt.pos.mypos.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {
    User findOneByUserName(String userName);

    Page<User> findByLastNameLikeOrFirstNameLike(String lastName, String firstName, Pageable page);
}
