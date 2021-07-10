package calculator;

import calculator.model.Measurement;
import calculator.model.Order;

import java.util.Arrays;

public class Calculator {
    private final double[] data;
    private final double error;
    private final boolean isAbsolute;
    private static final double[] GRUBBS = new double[] {
        1.155, 1.481, 1.715, 1.887, 2.02, 2.126, 2.215, 2.29, 2.355, 2.412,
        2.462, 2.507, 2.549, 2.585, 2.62, 2.651, 2.681, 2.709, 2.733, 2.758,
        2.781, 2.802, 2.822, 2.841, 2.859, 2.876, 2.893, 2.908};

    public Calculator(Order order) {
        this.data = order.getOrderData();
        this.error = order.getErrorValue();
        this.isAbsolute = order.isAbsolute();
    }

    public Measurement getMeasurement() {
        var rsl = new Measurement();
        rsl.setData(data);
        rsl.setMeasureCount(getCount());
        rsl.setAverage(getAverage(rsl.getMeasureCount()));
        rsl.setUncertaintyA(getUncertaintyA(
                rsl.getAverage(), rsl.getMeasureCount()));
        rsl.setUncertaintyB(getUncertaintyB(rsl.getAverage()));
        rsl.setUncertaintySum(getUncertaintySum(rsl.getUncertaintyA(),
                rsl.getUncertaintyB()));
        rsl.setUncertaintyDoubleSideExpanded(rsl.getUncertaintySum() * 2);
        rsl.setUncertaintySingleSideExpanded(rsl.getUncertaintySum() * 1.64);
        //rsl.setMinMiss(getMinMiss(rsl.getAverage(), rsl.getUncertaintyA(),
        //        rsl.getMeasureCount()));
        //rsl.setMaxMiss(getMaxMiss(rsl.getAverage(), rsl.getUncertaintyA(),
        //        rsl.getMeasureCount()));
        return rsl;
    }

    private int getCount() {
        return data.length;
    }

    private double getAverage(int count) {
        return Arrays.stream(data).reduce(Double::sum).orElse(0) / count;
    }

    private double getUncertaintyA(double avg, int count) {
        var sqrDeltaSum = Arrays.stream(data).map(x -> Math.pow(x - avg, 2))
                .reduce(Double::sum).orElse(0);
        var denominator = (count > 8) ? (count * (count - 1)) : (count - 1);
        return denominator > 0 ? Math.sqrt(sqrDeltaSum / denominator) : 0;
    }

    private double getUncertaintyB(double avg) {
        double rsl;
        if(isAbsolute) {
            rsl = error / Math.sqrt(3);
        } else {
            rsl = (avg / 100 * error) / Math.sqrt(3);
        }
        return rsl;
    }

    private double getUncertaintySum(double uncA, double uncB) {
        if (this.getCount() == 1 || isHomo()) {
            return uncB;
        } else {
            return Math.sqrt(Math.pow(uncA, 2) + Math.pow(uncB, 2));
        }
    }

    private String getMinMiss(double avg, double uncA, int count) {
        var min = Arrays.stream(data).reduce(Math::min).orElse(0);
        var kGrubbs = (avg - min) / uncA;
        var kNorm = GRUBBS[count - 3];
        return kGrubbs > kNorm ? "ПРОМАХ" : "НЕ ПРОМАХ";
    }

    private String getMaxMiss(double avg, double uncA, int count) {
        var max = Arrays.stream(data).reduce(Math::max).orElse(0);
        var kGrubbs = (max - avg) / uncA;
        var kNorm = GRUBBS[count - 3];
        return kGrubbs > kNorm ? "ПРОМАХ" : "НЕ ПРОМАХ";
    }

    private boolean isHomo(){
        boolean rsl = true;
        var firstValue = data[0];
        for (int i = 1; i < data.length; i++) {
            if(firstValue != data[i]) {
                rsl = false;
                break;
            }
        }
        return rsl;
    }

}