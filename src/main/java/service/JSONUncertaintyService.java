package service;

import calculator.Calculator;
import calculator.model.Order;
import com.google.gson.Gson;

public class JSONUncertaintyService implements UncertaintyService {
    @Override
    public String getUncertainty(String data) {
        var order = new Gson().fromJson(data, Order.class);
        var measurement = new Calculator(order).getMeasurement();
        return new Gson().toJson(measurement);
    }
}
