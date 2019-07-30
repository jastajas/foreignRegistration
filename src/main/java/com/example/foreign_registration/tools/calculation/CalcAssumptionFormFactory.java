package com.example.foreign_registration.tools.calculation;

import com.example.foreign_registration.model.assessment.PackageSize;
import com.example.foreign_registration.model.calculation.*;
import com.example.foreign_registration.repository.calculation.CalculationAssumptionsRepository;
import com.example.foreign_registration.repository.calculation.CalculationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CalcAssumptionFormFactory {

    private CalculationAssumptionsRepository calculationAssumptionsRepo;


    public CalculationAssumptionForm createCalculationAssumptionForm(Calculation calculation) {

        CalculationAssumptionForm calculationAssumptionForm = new CalculationAssumptionForm();

        calculationAssumptionsRepo
                .getMainCalcAssumtionsByCalculation(calculation)
                .stream()
                .forEach(calculationAssumptions -> {
                    switch (calculationAssumptions.getAssumptionCalcType()){
                        case Inflows:
                            calculationAssumptionForm.setAdditionalInflows(calculationAssumptions.getValues());
                            calculationAssumptionForm.setInflowsCurrency(calculationAssumptions.getCurrency().name());
                        case Outflows:
                            calculationAssumptionForm.setAdditionalOutflows(calculationAssumptions.getValues());
calculationAssumptionForm.setOutflowsCurrency(calculationAssumptions.getCurrency().name());
                        case DiscountRate:
                            calculationAssumptionForm.setDiscountRate(calculationAssumptions.getValues().get(0)); //nullPointer

                    }
                });

        return new CalculationAssumptionForm();
    }

//        switch(assumptions.getAssumptionCalcType())
//
//    {
//        case DiscountRate:
//            calculationAssumptionForm.setDiscountRate(assumptions.getSingleNoValue());
//            break;
//        case InitialOutlay:
//            calculationAssumptionForm.setInitialOutlay(assumptions.getSingleNoValue());
//            calculationAssumptionForm.setInitialOutlayCurrency(assumptions.getCurrency());
//            break;
//        case MarginOnRowMaterials:
//            calculationAssumptionForm.setMarginOnRowMaterials(assumptions.getSingleNoValue());
//            break;
//        case MarginOnManHours:
//            calculationAssumptionForm.setMarginOnRowMaterials(assumptions.getSingleNoValue());
//            break;
//    }
//
//
//
//        return new
//
//    CalculationAssumptionForm();
//}

    /*public CalculationAssumptionForm buildCalculationAssumptionForm(List<CalculationAssumptions> calculationAssumptions, List<PackageSize> packageSizes) {

        //calculationAssumptions.stream().filter(calculationAssumption -> null == calculationAssumption.getPackageSize()).collect(Collectors.toList());
        List<CalculationAssumptions> calcAssumptionForPacks = new ArrayList<>();

        CalculationAssumptionForm calculationAssumptionForm = new CalculationAssumptionForm();



        for (CalculationAssumptions assumptions : calculationAssumptions) {
            if (null != assumptions.getPackageSize()) {
                calcAssumptionForPacks.add(assumptions);
                continue;
            }
            switch (assumptions.getAssumptionCalcType()) {
                case DiscountRate:
                    calculationAssumptionForm.setDiscountRate(assumptions.getSingleNoValue());
                    break;
                case InitialOutlay:
                    calculationAssumptionForm.setInitialOutlay(assumptions.getSingleNoValue());
                    calculationAssumptionForm.setInitialOutlayCurrency(assumptions.getCurrency());
                    break;
                case MarginOnRowMaterials:
                    calculationAssumptionForm.setMarginOnRowMaterials(assumptions.getSingleNoValue());
                    break;
                case MarginOnManHours:
                    calculationAssumptionForm.setMarginOnRowMaterials(assumptions.getSingleNoValue());
                    break;
            }

        }
        calculationAssumptionForm.setPackageCalcAssumptions(buildPackageCalcAssumption(calcAssumptionForPacks,packageSizes));

        return calculationAssumptionForm;
    }

    public List<PackageCalcAssumption> buildPackageCalcAssumption(List<CalculationAssumptions> calculationAssumptions, List<PackageSize> packageSizes) {

        List<PackageCalcAssumption> pcAssumptionList = new ArrayList<>();

        for (PackageSize packageSize : packageSizes) {
            PackageCalcAssumption packCalcAssumptions = new PackageCalcAssumption(packageSize, (byte) 5);
            for (CalculationAssumptions calculationAssumption : calculationAssumptions) {

                if (!calculationAssumption.getPackageSize().equals(packageSize)) {
                    continue;
                }
                AssumptionCalcType currentType = calculationAssumption.getAssumptionCalcType();

                double[] tempArray = new double[0];

                if (currentType.equals(AssumptionCalcType.Inflow) || currentType.equals(AssumptionCalcType.Outflow) ||
                        currentType.equals(AssumptionCalcType.SalesPrice) || currentType.equals(AssumptionCalcType.TotalManufacturinCost) ||
                        currentType.equals(AssumptionCalcType.TechnicalManufacturinCost)) {

                    tempArray = new double[]{calculationAssumption.getFirstYear(), calculationAssumption.getSecondYear(),
                            calculationAssumption.getThirdYear(), calculationAssumption.getFourthYear(),
                            calculationAssumption.getFifthYear()};
                }

                switch (calculationAssumption.getAssumptionCalcType()) {
                    case Inflow:
                        packCalcAssumptions.setInflow(tempArray);
                        packCalcAssumptions.setInflowCurrency(calculationAssumption.getCurrency());
                        break;
                    case Outflow:
                        packCalcAssumptions.setOutflow(tempArray);
                        packCalcAssumptions.setOutflowCurrency(calculationAssumption.getCurrency());
                        break;
                    case SalesPrice:
                        packCalcAssumptions.setSalesPrice(tempArray);
                        packCalcAssumptions.setSalesPriceCurrency(calculationAssumption.getCurrency());
                        break;
                    case TotalManufacturinCost:
                        packCalcAssumptions.setTotalManufacturingCost(tempArray);
                        packCalcAssumptions.setTotalMCCurrency(calculationAssumption.getCurrency());
                        break;
                    case TechnicalManufacturinCost:
                        packCalcAssumptions.setTechnicalManufacturingCost(tempArray);
                        packCalcAssumptions.setTechMCCurrency(calculationAssumption.getCurrency());
                        break;
                    case Forecasts:
                        int[] forecasts = {(int) calculationAssumption.getFirstYear(), (int) calculationAssumption.getSecondYear(),
                                (int) calculationAssumption.getThirdYear(), (int) calculationAssumption.getFourthYear(),
                                (int) calculationAssumption.getFifthYear()};
                        packCalcAssumptions.setForecast(forecasts);
                }
            }
            pcAssumptionList.add(packCalcAssumptions);
        }
        return pcAssumptionList;
    }*/

}
