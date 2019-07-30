package com.example.foreign_registration.model.calculation;

import com.example.foreign_registration.model.assessment.AssessmentCostMH;

import javax.persistence.*;

@Entity
public class SelectedAsessmentCostMh {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Calculation calculation;

   @ManyToOne
   private AssessmentCostMH assessmentCostMH;

   public SelectedAsessmentCostMh() {
   }

   public SelectedAsessmentCostMh(Calculation calculation, AssessmentCostMH assessmentCostMH) {
      this.calculation = calculation;
      this.assessmentCostMH = assessmentCostMH;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Calculation getCalculation() {
      return calculation;
   }

   public void setCalculation(Calculation calculation) {
      this.calculation = calculation;
   }

   public AssessmentCostMH getAssessmentCostMH() {
      return assessmentCostMH;
   }

   public void setAssessmentCostMH(AssessmentCostMH assessmentCostMH) {
      this.assessmentCostMH = assessmentCostMH;
   }
}
