package com.github.springsecuritydemo.repository;

import com.github.springsecuritydemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findOneByLogin(String login);

}
