package com.example.foreign_registration.model.assessment;

import com.example.foreign_registration.model.app.AppObjectForm;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssessmentForm extends AppObjectForm {

    private long idAssessment;
    private long idCountry;
    private long idDestinedProdStatus;
    private long idRequiredProdQualif;

    private String avaliabilityStatus;

    private boolean sModule;
    private boolean quality;
    private boolean clinic;
    private boolean nonClinic;

    public long getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(long idAssessment) {
        this.idAssessment = idAssessment;
    }

    public long getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(long idCountry) {
        this.idCountry = idCountry;
    }

    public long getIdDestinedProdStatus() {
        return idDestinedProdStatus;
    }

    public void setIdDestinedProdStatus(long idDestinedProdStatus) {
        this.idDestinedProdStatus = idDestinedProdStatus;
    }

    public long getIdRequiredProdQualif() {
        return idRequiredProdQualif;
    }

    public void setIdRequiredProdQualif(long idRequiredProdQualif) {
        this.idRequiredProdQualif = idRequiredProdQualif;
    }

    public String getAvaliabilityStatus() {
        return avaliabilityStatus;
    }

    public void setAvaliabilityStatus(String avaliabilityStatus) {
        this.avaliabilityStatus = avaliabilityStatus;
    }

    public boolean issModule() {
        return sModule;
    }

    public void setsModule(boolean sModule) {
        this.sModule = sModule;
    }

    public boolean isQuality() {
        return quality;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }

    public boolean isClinic() {
        return clinic;
    }

    public void setClinic(boolean clinic) {
        this.clinic = clinic;
    }

    public boolean isNonClinic() {
        return nonClinic;
    }

    public void setNonClinic(boolean nonClinic) {
        this.nonClinic = nonClinic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AssessmentForm that = (AssessmentForm) o;
        return idAssessment == that.idAssessment &&
                idCountry == that.idCountry &&
                idDestinedProdStatus == that.idDestinedProdStatus &&
                idRequiredProdQualif == that.idRequiredProdQualif &&
                sModule == that.sModule &&
                quality == that.quality &&
                clinic == that.clinic &&
                nonClinic == that.nonClinic &&
                Objects.equals(avaliabilityStatus, that.avaliabilityStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idAssessment, idCountry, idDestinedProdStatus, idRequiredProdQualif, avaliabilityStatus, sModule, quality, clinic, nonClinic);
    }

    @Override
    public String toString() {
        return "AssessmentForm{" +
                "idAssessment=" + idAssessment +
                ", idCountry=" + idCountry +
                ", idDestinedProdStatus=" + idDestinedProdStatus +
                ", idRequiredProdQualif=" + idRequiredProdQualif +
                ", avaliabilityStatus='" + avaliabilityStatus + '\'' +
                ", sModule=" + sModule +
                ", quality=" + quality +
                ", clinic=" + clinic +
                ", nonClinic=" + nonClinic +
                '}';
    }

    public boolean isValid() {

        if (this.idCountry == 0 || this.idDestinedProdStatus == 0 || getCreatorUsername() == null || "".equals(getCreatorUsername()) ||
                this.avaliabilityStatus == null || "".equals(avaliabilityStatus)) {
            return false;
        }

        return true;
    }
}
