package com.spme.dao;

import com.spme.Entity.QuestionEntity;
import com.spme.Entity.QuestionMultiKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<QuestionEntity, QuestionMultiKeys> {
    List<QuestionEntity> findByLabAndStepAndLowerLab(String lab, int step, int lowerLab);
}
