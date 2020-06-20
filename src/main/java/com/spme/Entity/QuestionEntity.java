package com.spme.Entity;

import javax.persistence.*;

@Entity
@Table(name = "question")
@IdClass(QuestionMultiKeys.class)
public class QuestionEntity {
    @Id
    @Column(name = "lab")
    String lab;

    @Id
    @Column(name = "lowerLab")
    int lowerLab;

    @Id
    @Column(name = "questionId")
    int questionId;

    @Id
    @Column(name = "step")
    int step;

    @Column(name = "question")
    String question;

    public QuestionEntity() {
    }

    public String getLab() {
        return lab;
    }

    public int getLowerLab() {
        return lowerLab;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getStep() {
        return step;
    }

    public String getQuestion() {
        return question;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setLowerLab(int lowerLab) {
        this.lowerLab = lowerLab;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
