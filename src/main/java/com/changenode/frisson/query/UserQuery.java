package com.changenode.frisson.query;

import com.changenode.frisson.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuery extends JpaRepository<User, String> {
}