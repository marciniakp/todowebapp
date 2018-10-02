package com.betapp.repositories;

import com.betapp.model.TODO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TODORepository extends CrudRepository<TODO, Long> {
}
