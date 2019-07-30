package com.example.foreign_registration.tools.assessment;

import com.example.foreign_registration.model.app.ProductQualification;
import com.example.foreign_registration.model.assessment.Assessment;
import com.example.foreign_registration.model.assessment.AssessmentForm;
import com.example.foreign_registration.model.exceptions.BuildingAppObjectException;
import com.example.foreign_registration.model.exceptions.OversizeNumberException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssessmentFactoryTest {

    @Autowired
    AssessmentFactory assessmentFactory;


    @Test
    public void createNumber() {

    }

    @Test
    public void showGenericQualification() {
        //given
        AssessmentForm assessmentForm = new AssessmentForm();
        assessmentForm.setIdCountry(2);
        assessmentForm.setIdDestinedProdStatus(1);
        assessmentForm.setIdRequiredProdQualif(3);
        assessmentForm.setAvaliabilityStatus("OTC");
        assessmentForm.setCreatorUsername("j.kowalski@example.com");
        //when
        String qulificationName = null;
        try {
            qulificationName = Optional
                    .of((Assessment) assessmentFactory.create(assessmentForm))
                    .map(Assessment::getRequired_prod_qualification)
                    .map(ProductQualification::getCategory_name)
                    .orElse("");
        } catch (BuildingAppObjectException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (OversizeNumberException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //then
        Assert.assertThat(qulificationName, CoreMatchers.equalTo("Generyk"));
    }
}
