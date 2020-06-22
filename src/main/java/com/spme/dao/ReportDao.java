package com.spme.dao;

import com.spme.Entity.ReportEntity;
import com.spme.Entity.ReportMultiKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDao extends JpaRepository<ReportEntity, ReportMultiKeys> {
    List<ReportEntity> findAll();
    ReportEntity findByUidAndLabAndStepAndLowerLabAndQuestionId(String uid, String lab, int step, int lowerLab, int questionId);
    List<ReportEntity> findByUidAndLabAndStepAndLowerLab(String uid, String lab, int step, int lowerLab);
    List<ReportEntity> findByUidAndLab(String uid, String lab);
}
