package calculator.model;

public class Order {
    private boolean isAbsolute;
    private double errorValue;
    private double[] orderData;

    public boolean isAbsolute() {
        return isAbsolute;
    }

    public void setAbsolute(boolean absolute) {
        isAbsolute = absolute;
    }

    public double getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(double errorValue) {
        this.errorValue = errorValue;
    }

    public double[] getOrderData() {
        return orderData;
    }

    public void setOrderData(double[] orderData) {
        this.orderData = orderData;
    }
}
