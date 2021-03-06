package com.example.foreign_registration.model.process;

import com.example.foreign_registration.model.app.*;
import com.example.foreign_registration.model.assessment.Assessment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Process extends AppObject {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number;*/

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "process_assessment",
            joinColumns = {@JoinColumn(name = "process_id")},
            inverseJoinColumns = {@JoinColumn(name = "assessment_id")})
    @JsonIgnore
    private List<Assessment> assessments;

    @ManyToOne
    private Product product;

    private String destined_product_name;

    @ManyToOne
    private ProductQualification product_qualification;
    @ColumnDefault("0")
    private long one_order_amount;

    @ManyToOne
    private Unit one_order_unit;
    @ColumnDefault("0")
    private long annual_order_amount;

    @ManyToOne
    private Unit annual_order_unit;

    private String contact_person_name;

    private String contact_person_data;

    private String agency_adress;

 /*   @ManyToOne
    private Status status;
*/
    @ManyToOne
    private Client client;

   /* @ManyToOne
    private User creator;*/

    @Enumerated(EnumType.STRING)
    private ModelCooperation model_cooperation;

   /* private Date creationDate;*/
    @JsonIgnore
    @OneToMany(mappedBy = "process")
    private List<Task> taskList;

    public Process() {
    }

    public Process(String number, List<Assessment> assessments, Product product, String destined_product_name, ProductQualification product_qualification, long one_order_amount, Unit one_order_unit, long annual_order_amount, Unit annual_order_unit, String contact_person_name, String contact_person_data, String agency_adress, Status status, Client client, User creator, ModelCooperation model_cooperation, Date creationDate, List<Task> taskList) {
        super(number, status, creationDate, creator);
//         this.number = number;
        this.assessments = assessments;
        this.product = product;
        this.destined_product_name = destined_product_name;
        this.product_qualification = product_qualification;
        this.one_order_amount = one_order_amount;
        this.one_order_unit = one_order_unit;
        this.annual_order_amount = annual_order_amount;
        this.annual_order_unit = annual_order_unit;
        this.contact_person_name = contact_person_name;
        this.contact_person_data = contact_person_data;
        this.agency_adress = agency_adress;
//         this.status = status;
        this.client = client;
//         this.creator = creator;
        this.model_cooperation = model_cooperation;
//         this.creationDate = creationDate;
        this.taskList = taskList;
    }

    public Process(String number, Product product, String destined_product_name, ProductQualification product_qualification, Status status, Client client, User creator, ModelCooperation model_cooperation, Date creationDate) {
        super(number, status, creationDate, creator);
//        this.number = number;
        this.product = product;
        this.destined_product_name = destined_product_name;
        this.product_qualification = product_qualification;
//        this.status = status;
        this.client = client;
//        this.creator = creator;
        this.model_cooperation = model_cooperation;
//        this.creationDate = creationDate;
    }

 /*   public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }*/

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDestined_product_name() {
        return destined_product_name;
    }

    public void setDestined_product_name(String destined_product_name) {
        this.destined_product_name = destined_product_name;
    }

    public ProductQualification getProduct_qualification() {
        return product_qualification;
    }

    public void setProduct_qualification(ProductQualification product_qualification) {
        this.product_qualification = product_qualification;
    }

    public long getOne_order_amount() {
        return one_order_amount;
    }

    public void setOne_order_amount(long one_order_amount) {
        this.one_order_amount = one_order_amount;
    }

    public Unit getOne_order_unit() {
        return one_order_unit;
    }

    public void setOne_order_unit(Unit one_order_unit) {
        this.one_order_unit = one_order_unit;
    }

    public long getAnnual_order_amount() {
        return annual_order_amount;
    }

    public void setAnnual_order_amount(long annual_order_amount) {
        this.annual_order_amount = annual_order_amount;
    }

    public Unit getAnnual_order_unit() {
        return annual_order_unit;
    }

    public void setAnnual_order_unit(Unit annual_order_unit) {
        this.annual_order_unit = annual_order_unit;
    }

    public String getContact_person_name() {
        return contact_person_name;
    }

    public void setContact_person_name(String contact_person_name) {
        this.contact_person_name = contact_person_name;
    }

    public String getContact_person_data() {
        return contact_person_data;
    }

    public void setContact_person_data(String contact_person_data) {
        this.contact_person_data = contact_person_data;
    }

    public String getAgency_adress() {
        return agency_adress;
    }

    public void setAgency_adress(String agency_adress) {
        this.agency_adress = agency_adress;
    }

   /* public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }*/

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

   /* public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }*/

    public ModelCooperation getModel_cooperation() {
        return model_cooperation;
    }

    public void setModel_cooperation(ModelCooperation model_cooperation) {
        this.model_cooperation = model_cooperation;
    }

   /* public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }*/

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return id == process.id &&
                one_order_amount == process.one_order_amount &&
                annual_order_amount == process.annual_order_amount &&
                Objects.equals(number, process.number) &&
                Objects.equals(product, process.product) &&
                Objects.equals(destined_product_name, process.destined_product_name) &&
                Objects.equals(product_qualification, process.product_qualification) &&
                Objects.equals(one_order_unit, process.one_order_unit) &&
                Objects.equals(annual_order_unit, process.annual_order_unit) &&
                Objects.equals(contact_person_name, process.contact_person_name) &&
                Objects.equals(contact_person_data, process.contact_person_data) &&
                Objects.equals(agency_adress, process.agency_adress) &&
                Objects.equals(status, process.status) &&
                Objects.equals(client, process.client) &&
                Objects.equals(creator, process.creator) &&
                model_cooperation == process.model_cooperation &&
                Objects.equals(creationDate, process.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, product, destined_product_name, product_qualification, one_order_amount, one_order_unit, annual_order_amount, annual_order_unit, contact_person_name, contact_person_data, agency_adress, status, client, creator, model_cooperation, creationDate);
    }*/
}
