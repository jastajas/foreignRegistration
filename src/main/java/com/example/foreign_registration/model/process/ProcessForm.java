package com.example.foreign_registration.model.process;

import com.example.foreign_registration.model.app.AppObjectForm;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProcessForm extends AppObjectForm {

    private long idProcess;
    private long idProduct;
    private long idClient;
    private long idProductQualification;

    private String destinedProductName;
    private String modelCooperation;

    public long getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(long idProcess) {
        this.idProcess = idProcess;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(long idClient) {
        this.idClient = idClient;
    }

    public long getIdProductQualification() {
        return idProductQualification;
    }

    public void setIdProductQualification(long idProductQualification) {
        this.idProductQualification = idProductQualification;
    }

    public String getDestinedProductName() {
        return destinedProductName;
    }

    public void setDestinedProductName(String destinedProductName) {
        this.destinedProductName = destinedProductName;
    }

    public String getModelCooperation() {
        return modelCooperation;
    }

    public void setModelCooperation(String modelCooperation) {
        this.modelCooperation = modelCooperation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProcessForm that = (ProcessForm) o;
        return idProcess == that.idProcess &&
                idProduct == that.idProduct &&
                idClient == that.idClient &&
                idProductQualification == that.idProductQualification &&
                Objects.equals(destinedProductName, that.destinedProductName) &&
                Objects.equals(modelCooperation, that.modelCooperation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idProcess, idProduct, idClient, idProductQualification, destinedProductName, modelCooperation);
    }

    @Override
    public String toString() {
        return "ProcessForm{" +
                "idProcess=" + idProcess +
                ", idProduct=" + idProduct +
                ", idClient=" + idClient +
                ", idProductQualification=" + idProductQualification +
                ", destinedProductName='" + destinedProductName + '\'' +
                ", modelCooperation='" + modelCooperation + '\'' +
                '}';
    }

    @Override
    public boolean isValid() {
            if(this.idProduct == 0 || this.idClient == 0 || this.modelCooperation == null ||
                    "".equals(modelCooperation) || this.destinedProductName == null){
                return false;
            }
            return true;
    }
}
