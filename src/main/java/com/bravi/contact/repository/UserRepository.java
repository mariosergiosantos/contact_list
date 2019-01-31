package com.bravi.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bravi.contact.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
