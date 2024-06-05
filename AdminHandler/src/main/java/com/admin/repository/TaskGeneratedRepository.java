package com.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.entity.TaskGenerated;

@Repository
public interface TaskGeneratedRepository extends JpaRepository<TaskGenerated, Long> {

}
