package com.example.foreign_registration.tools.calculation;

import com.example.foreign_registration.model.app.Currency;
import com.example.foreign_registration.model.assessment.AssessmentCostMH;
import com.example.foreign_registration.model.calculation.CurrencyRate;
import com.example.foreign_registration.repository.assessment.AssessmentCostMHRepository;
import com.example.foreign_registration.repository.assessment.DepartmentAssessmentRepository;
import com.example.foreign_registration.repository.calculation.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MhCostCalculator {


    private AssessmentCostMHRepository assessmentCostMHRepository;
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    public MhCostCalculator(AssessmentCostMHRepository assessmentCostMHRepository, CurrencyRateRepository currencyRateRepository) {
        this.assessmentCostMHRepository = assessmentCostMHRepository;
        this.currencyRateRepository = currencyRateRepository;
    }

    public double getSumCostForAssessment(long assessmentId) {

        double sum = 0;
        for (Currency value : Currency.values()) {
            Optional<Double> tempSum = assessmentCostMHRepository.getSumCostAssessment(assessmentId, value);
            Optional<CurrencyRate> currencyRate = currencyRateRepository.getCurrencyRateByCurrency(value);

            if (tempSum.isPresent() && currencyRate.isPresent()) {
                sum += (tempSum.get() * currencyRate.get().getRate());
            }
        }

        BigDecimal bd = new BigDecimal(sum);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int getSumMhForAssessment(long assessmentID){

        Optional<Integer> sumMhOptional = assessmentCostMHRepository.getSumMhAssessment(assessmentID);

        if (!sumMhOptional.isPresent()){
            return 0;
        }

        return sumMhOptional.get();
    }
}
