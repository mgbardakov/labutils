package calculator;

import calculator.model.Measurement;

import java.util.Arrays;

public class Calculator {
    private double[] data;
    private double error;
    private boolean isAbsolute;

    public Calculator(double[] data, double error, boolean isAbsolute) {
        this.data = data;
        this.error = error;
        this.isAbsolute = isAbsolute;
    }

    public Measurement getMeasurement() {
        var rsl = new Measurement();
        rsl.setMeasureCount(getCount());
        rsl.setAverage(getAverage());
        rsl.setUncertaintyA(getUncertaintyA(
                rsl.getAverage(), rsl.getMeasureCount()));
        rsl.setUncertaintyB(getUncertaintyB(rsl.getAverage()));
        rsl.setUncertaintySum(getUncertaintySum(rsl.getUncertaintyA(),
                rsl.getUncertaintyB()));
        rsl.setUncertaintyDoubleSideExpanded(rsl.getUncertaintySum() * 2);
        rsl.setUncertaintySingleSideExpanded(rsl.getUncertaintySum() * 1.64);
        return rsl;
    }

    private int getCount() {
        return data.length;
    }

    private double getAverage() {
        return Arrays.stream(data).reduce(Double::sum).orElse(0);
    }

    private double getUncertaintyA(double avg, int count) {
        var sqrDeltaSum = Arrays.stream(data).reduce((x, y) ->
                Math.pow(x - avg, 2) + Math.pow(y - avg, 2)).orElse(0);
        return Math.sqrt(sqrDeltaSum / count * (count - 1));
    }

    private double getUncertaintyB(double avg) {
        var rsl = 0.d;
        if(isAbsolute) {
            rsl = error / Math.sqrt(3);
        } else {
            rsl = (avg / 100 * error) / Math.sqrt(3);
        }
        return rsl;
    }

    private double getUncertaintySum(double uncA, double uncB) {
        return Math.sqrt(Math.pow(uncA, 2) + Math.pow(uncB, 2));
    }
}