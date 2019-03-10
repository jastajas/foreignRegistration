package com.example.foreign_registration.model.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.foreign_registration.model.process.Process;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String drug_form;
    private String technological_code;
    private String item_no;

    @ManyToOne
    private ProductStatus product_status;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Process> processes;

    public Product(String name, String drug_form, String technological_code, String item_no, ProductStatus product_status, List<Process> processes) {
        this.name = name;
        this.drug_form = drug_form;
        this.technological_code = technological_code;
        this.item_no = item_no;
        this.product_status = product_status;
        this.processes = processes;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrug_form() {
        return drug_form;
    }

    public void setDrug_form(String drug_form) {
        this.drug_form = drug_form;
    }

    public String getTechnological_code() {
        return technological_code;
    }

    public void setTechnological_code(String technological_code) {
        this.technological_code = technological_code;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public ProductStatus getProduct_status() {
        return product_status;
    }

    public void setProduct_status(ProductStatus product_status) {
        this.product_status = product_status;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(drug_form, product.drug_form) &&
                Objects.equals(technological_code, product.technological_code) &&
                Objects.equals(item_no, product.item_no) &&
                Objects.equals(product_status, product.product_status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, drug_form, technological_code, item_no, product_status);
    }
}
