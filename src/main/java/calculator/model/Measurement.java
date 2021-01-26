package calculator.model;

public class Measurement {
    private double[] data;
    private double average;
    private int measureCount;
    private double uncertaintyA;
    private double uncertaintyB;
    private double uncertaintySum;
    private double uncertaintySingleSideExpanded;
    private double uncertaintyDoubleSideExpanded;
    private String minMiss;
    private String maxMiss;

    public Measurement() {
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public double getUncertaintyA() {
        return uncertaintyA;
    }

    public void setUncertaintyA(double uncertaintyA) {
        this.uncertaintyA = uncertaintyA;
    }

    public double getUncertaintyB() {
        return uncertaintyB;
    }

    public void setUncertaintyB(double uncertaintyB) {
        this.uncertaintyB = uncertaintyB;
    }

    public double getUncertaintySum() {
        return uncertaintySum;
    }

    public void setUncertaintySum(double uncertaintySum) {
        this.uncertaintySum = uncertaintySum;
    }

    public double getUncertaintySingleSideExpanded() {
        return uncertaintySingleSideExpanded;
    }

    public void setUncertaintySingleSideExpanded(double uncertaintySingleSideExpanded) {
        this.uncertaintySingleSideExpanded = uncertaintySingleSideExpanded;
    }

    public double getUncertaintyDoubleSideExpanded() {
        return uncertaintyDoubleSideExpanded;
    }

    public void setUncertaintyDoubleSideExpanded(double uncertaintyDoubleSideExpanded) {
        this.uncertaintyDoubleSideExpanded = uncertaintyDoubleSideExpanded;
    }

    public String getMinMiss() {
        return minMiss;
    }

    public void setMinMiss(String minMiss) {
        this.minMiss = minMiss;
    }

    public String getMaxMiss() {
        return maxMiss;
    }

    public void setMaxMiss(String maxMiss) {
        this.maxMiss = maxMiss;
    }
}
