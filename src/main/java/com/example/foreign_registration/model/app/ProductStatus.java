package com.example.foreign_registration.model.app;

import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.AssessmentPattern;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class ProductStatus {
    //produkt leczniczy, wyrób medyczny

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product_status;

    @OneToMany(mappedBy = "product_status")
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy = "productStatus")
    @JsonIgnore
    private List<AssessmentPattern> assessmentPatterns;

    @OneToMany(mappedBy = "destined_product_status")
    @JsonIgnore
    private List<Assessment> assessments;


    public ProductStatus(String product_status, List<Product> products, List<AssessmentPattern> assessmentPatterns, List<Assessment> assessments) {
        this.product_status = product_status;
        this.products = products;
        this.assessmentPatterns = assessmentPatterns;
        this.assessments = assessments;
    }

    public ProductStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<AssessmentPattern> getAssessmentPatterns() {
        return assessmentPatterns;
    }

    public void setAssessmentPatterns(List<AssessmentPattern> assessmentPatterns) {
        this.assessmentPatterns = assessmentPatterns;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStatus that = (ProductStatus) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(product_status, that.product_status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_status);
    }
}
