package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.CriticalityScale;
import com.example.foreign_registration.model.app.Department;
import com.example.foreign_registration.model.app.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class DepartmentAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assessmentDescription;

    @ManyToOne
    private User author;

    @ManyToOne
    private AssessmentPattern assessmentPattern;

    private String otherAssessmentSubject;

    @ManyToOne
    private Department other_subject_department;

    @ManyToOne
    private Assessment assessment;

    private Date assessment_date;

    @Enumerated(EnumType.STRING)
    private CriticalityScale criticalityScale;

    @OneToMany(mappedBy = "department_assessment")
    @JsonIgnore
    private List<AssessmentCostMH> assessmentCostMHs;

    public DepartmentAssessment() {
    }

    public DepartmentAssessment(String assessmentDescription, User author, AssessmentPattern assessmentPattern,
                                String otherAssessmentSubject, Department other_subject_department, Assessment assessment,
                                Date assessment_date, CriticalityScale criticalityScale, List<AssessmentCostMH> assessmentCostMHs) {
        this.assessmentDescription = assessmentDescription;
        this.author = author;
        this.assessmentPattern = assessmentPattern;
        this.otherAssessmentSubject = otherAssessmentSubject;
        this.other_subject_department = other_subject_department;
        this.assessment = assessment;
        this.assessment_date = assessment_date;
        this.criticalityScale = criticalityScale;
        this.assessmentCostMHs = assessmentCostMHs;
    }

    public DepartmentAssessment(AssessmentPattern assessmentPattern, Assessment assessment) {
        this.assessmentPattern = assessmentPattern;
        this.assessment = assessment;
    }

    public DepartmentAssessment(String otherAssessmentSubject, Department other_subject_department, Assessment assessment){
        this.otherAssessmentSubject = otherAssessmentSubject;
        this.other_subject_department = other_subject_department;
        this.assessment = assessment;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessmentDescription() {
        return assessmentDescription;
    }

    public void setAssessmentDescription(String assessmentDescription) {
        this.assessmentDescription = assessmentDescription;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public AssessmentPattern getAssessmentPattern() {
        return assessmentPattern;
    }

    public void setAssessmentPattern(AssessmentPattern assessmentPattern) {
        this.assessmentPattern = assessmentPattern;
    }

    public String getOtherAssessmentSubject() {
        return otherAssessmentSubject;
    }

    public void setOtherAssessmentSubject(String otherAssessmentSubject) {
        this.otherAssessmentSubject = otherAssessmentSubject;
    }

    public Department getOther_subject_department() {
        return other_subject_department;
    }

    public void setOther_subject_department(Department other_subject_department) {
        this.other_subject_department = other_subject_department;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Date getAssessment_date() {
        return assessment_date;
    }

    public void setAssessment_date(Date assessment_date) {
        this.assessment_date = assessment_date;
    }

    public CriticalityScale getCriticalityScale() {
        return criticalityScale;
    }

    public void setCriticalityScale(CriticalityScale criticalityScale) {
        this.criticalityScale = criticalityScale;
    }

    public List<AssessmentCostMH> getAssessmentCostMHs() {
        return assessmentCostMHs;
    }

    public void setAssessmentCostMHs(List<AssessmentCostMH> assessmentCostMHs) {
        this.assessmentCostMHs = assessmentCostMHs;
    }

    public void setDescriptonAndDateAndCriticalityAndManHours(String assessmentDescription, Date assessment_date, CriticalityScale criticalityScale) {
        this.assessmentDescription = assessmentDescription;
        this.assessment_date = assessment_date;
        this.criticalityScale = criticalityScale;
    }

}
