package com.spme.Entity;

import javax.persistence.*;

@Entity
@Table(name = "report")
@IdClass(ReportMultiKeys.class)
public class ReportEntity {
    @Id
    @Column(name = "lab")
    String lab;

    @Id
    @Column(name = "uid")
    String uid;

    @Id
    @Column(name = "step")
    Integer step;

    @Id
    @Column(name = "lowerLab")
    Integer lowerLab;

    @Id
    @Column(name = "questionId")
    Integer questionId;

    @Column(name = "answer")
    String answer;

    @Column(name = "isDraft")
    String isDraft;

    public ReportEntity() {
    }

    public String getLab() {
        return lab;
    }

    public String getUid() {
        return uid;
    }

    public int getStep() {
        return step;
    }

    public int getLowerLab() {
        return lowerLab;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getIsDraft() {
        return isDraft;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setLowerLab(int lowerLab) {
        this.lowerLab = lowerLab;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsDraft(String isDraft) {
        this.isDraft = isDraft;
    }
}
