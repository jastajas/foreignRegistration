package com.example.foreign_registration.tools.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

public class ProfitabilityCalculator {


    public static double npvCalculator(double discountRate, double[] cashFlows, double initialOutlay) {

        double npv = 0;
        for (int i = 0; i < cashFlows.length; i++) {
            npv += (cashFlows[i] / discountIndexGen(discountRate, i + 1));
        }
        npv -= initialOutlay;
        return setDoubleScale(npv, 2);
    }

    private static double discountIndexGen(double discountRate, int period) {
        return pow((1 + discountRate), period);
    }

    public double piCalculator(double npv, double initialOutlay) {
        return setDoubleScale(npv - initialOutlay / initialOutlay, 2);
    }

    private static double setDoubleScale(double convertedValue, int scale) {

        BigDecimal bd = new BigDecimal(convertedValue);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();

    }

    public static double roiCalculator(double[] cashFlows, double initialOutlay) {

        double sumCashFlows = 0;

        for (double cashFlow : cashFlows) {
            sumCashFlows += cashFlow;
        }

        return setDoubleScale((sumCashFlows - initialOutlay) / initialOutlay * 100, 2);
    }

    public static double[] cashFlowsCalculator(double[] tmc, double[] salesPrice, int[] salesForecast, double[] inflow, double[] outflow) {

        if (tmc.length != salesForecast.length || tmc.length != salesPrice.length) {
            //throw new error
        }

        for (int i = 0; i < tmc.length; i++) {


        }


        double[] jakas = {0.22, 0.11};

        return jakas;
    }

}
