package net.nh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.nh.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
}
