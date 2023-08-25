package com.mkl.suaggerDemo.model;

/**
 * @author mkl
 */
public class GradeInfo {
    private Integer id;
    private String grade;
    private Integer delDlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getDelDlag() {
        return delDlag;
    }

    public void setDelDlag(Integer delDlag) {
        this.delDlag = delDlag;
    }

    @Override
    public String toString() {
        return "GradeInfo{" +
                "id=" + id +
                ", grade='" + grade + '\'' +
                ", delDlag=" + delDlag +
                '}';
    }
}
