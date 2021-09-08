package com.changenode.frisson.query;

import com.changenode.frisson.data.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodosEntityQuery extends JpaRepository<ToDo, Long> {
}