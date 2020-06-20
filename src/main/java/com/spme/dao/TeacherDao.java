package com.spme.dao;

import com.spme.Entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDao extends JpaRepository<TeacherEntity, String> {
    boolean existsById(String id);
}
