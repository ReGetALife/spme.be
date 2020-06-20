package com.spme.Entity;

import javax.persistence.*;

@Entity
@Table(name = "result")
@IdClass(ResultMultiKeys.class)
public class ResultEntity {
    @Id
    @Column(name = "uid")
    String uid;

    @Id
    @Column(name = "lab")
    String lab;

    @Column(name = "score")
    int score;

    @Column(name = "comment")
    String comment;

    @Column(name = "isRelease")
    int isRelease;

    public ResultEntity() {
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setIsRelease(int isRelease) {
        this.isRelease = isRelease;
    }

    public String getUid() {
        return uid;
    }

    public String getLab() {
        return lab;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public int getIsRelease() {
        return isRelease;
    }
}
