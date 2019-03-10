package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.calculation.Calculation;
import com.example.foreign_registration.model.process.Process;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String assessmentNo;

    @ManyToOne
    private Country registration_country;

    @ManyToOne
    private ProductStatus destined_product_status;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availability_status;

    @ManyToOne
    private ProductQualification required_prod_qualification;

    @OneToMany(mappedBy = "assessment")
    @JsonIgnore
    private List<Requirement> requirements;

    @OneToMany(mappedBy = "assessment")
    @JsonIgnore
    private List<PackageSize> package_sizes;

    @OneToMany(mappedBy = "assessment")
    @JsonIgnore
    private List<AssessDepartConfirmation> assessDepartConfirmations;

    @ManyToOne
    private User ordering_person;

    @ManyToOne
    private Status status;

    private Date order_date;

    @ManyToMany(mappedBy = "assessments")
    @JsonIgnore
    private List<Process> processes;

    @OneToMany(mappedBy = "assessment")
    @JsonIgnore
    private List<DepartmentAssessment> departmentAssessments;

    @OneToMany(mappedBy = "assessment")
    private List<Calculation> calculationList;

    private boolean s_module;
    private boolean clinical_module;
    private boolean nonclinical_module;
    private boolean quality_module;

    public Assessment() {
    }

    public Assessment(String assessmentNo, Country registration_country, ProductStatus destined_product_status,
                      AvailabilityStatus availability_status, ProductQualification required_prod_qualification,
                      List<Requirement> requirements,
                      List<PackageSize> package_sizes, List<AssessDepartConfirmation> assessDepartConfirmations,
                      User ordering_person, Status status, Date order_date, List<Process> processes,
                      List<DepartmentAssessment> departmentAssessments, List<Calculation> calculationList,
                      boolean s_module, boolean clinical_module,
                      boolean nonclinical_module, boolean quality_module) {
        this.assessmentNo = assessmentNo;
        this.registration_country = registration_country;
        this.destined_product_status = destined_product_status;
        this.availability_status = availability_status;
        this.required_prod_qualification = required_prod_qualification;
        this.requirements = requirements;
        this.package_sizes = package_sizes;
        this.assessDepartConfirmations = assessDepartConfirmations;
        this.ordering_person = ordering_person;
        this.status = status;
        this.order_date = order_date;
        this.processes = processes;
        this.departmentAssessments = departmentAssessments;
        this.s_module = s_module;
        this.clinical_module = clinical_module;
        this.nonclinical_module = nonclinical_module;
        this.quality_module = quality_module;
        this.calculationList = calculationList;
    }

    public Assessment(String assessmentNo, Country registration_country, ProductStatus destined_product_status, AvailabilityStatus availability_status, ProductQualification required_prod_qualification, User ordering_person, Status status, Date order_date) {
        this.assessmentNo = assessmentNo;
        this.registration_country = registration_country;
        this.destined_product_status = destined_product_status;
        this.availability_status = availability_status;
        this.required_prod_qualification = required_prod_qualification;
        this.ordering_person = ordering_person;
        this.status = status;
        this.order_date = order_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public Country getRegistration_country() {
        return registration_country;
    }

    public void setRegistration_country(Country registration_country) {
        this.registration_country = registration_country;
    }

    public ProductStatus getDestined_product_status() {
        return destined_product_status;
    }

    public void setDestined_product_status(ProductStatus destined_product_status) {
        this.destined_product_status = destined_product_status;
    }

    public AvailabilityStatus getAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(AvailabilityStatus availability_status) {
        this.availability_status = availability_status;
    }

    public ProductQualification getRequired_prod_qualification() {
        return required_prod_qualification;
    }

    public void setRequired_prod_qualification(ProductQualification required_prod_qualification) {
        this.required_prod_qualification = required_prod_qualification;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<PackageSize> getPackage_sizes() {
        return package_sizes;
    }

    public void setPackage_sizes(List<PackageSize> package_sizes) {
        this.package_sizes = package_sizes;
    }

    public List<AssessDepartConfirmation> getAssessDepartConfirmations() {
        return assessDepartConfirmations;
    }

    public void setAssessDepartConfirmations(List<AssessDepartConfirmation> assessDepartConfirmations) {
        this.assessDepartConfirmations = assessDepartConfirmations;
    }

    public User getOrdering_person() {
        return ordering_person;
    }

    public void setOrdering_person(User ordering_person) {
        this.ordering_person = ordering_person;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<DepartmentAssessment> getDepartmentAssessments() {
        return departmentAssessments;
    }

    public void setDepartmentAssessments(List<DepartmentAssessment> departmentAssessments) {
        this.departmentAssessments = departmentAssessments;
    }

    public boolean isS_module() {
        return s_module;
    }

    public void setS_module(boolean s_module) {
        this.s_module = s_module;
    }

    public boolean isClinical_module() {
        return clinical_module;
    }

    public void setClinical_module(boolean clinical_module) {
        this.clinical_module = clinical_module;
    }

    public boolean isNonclinical_module() {
        return nonclinical_module;
    }

    public void setNonclinical_module(boolean nonclinical_module) {
        this.nonclinical_module = nonclinical_module;
    }

    public boolean isQuality_module() {
        return quality_module;
    }

    public void setQuality_module(boolean quality_module) {
        this.quality_module = quality_module;
    }

    public Process getProcessById(long id) {

        for (Process process : processes) {
            if (id == process.getId()) {
                return process;
            }
        }
        return new Process();
    }

    public void addSimpleRequirement(Requirement regitrationRequirement) {

        if (null != regitrationRequirement && !"".equals(regitrationRequirement)) {
            this.requirements.add(regitrationRequirement);
        }
    }

}