package calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void getMeasurementByData() {
        var data = new double[]{4, 4.7, 5, 4.8, 5.3, 5.8, 4.1, 4.2, 5.7, 6, 5.5};
        var calculator = new Calculator(data, 0.7, true);
        var resultMeasurement = calculator.getMeasurement();
        assertEquals(11, resultMeasurement.getMeasureCount());
        assertEquals(5.01, resultMeasurement.getAverage(), 0.01);
        assertEquals(0.677, resultMeasurement.getUncertaintyA(), 0.01);
        assertEquals(0.4, resultMeasurement.getUncertaintyB(), 0.01);
        assertEquals(0.786, resultMeasurement.getUncertaintySum(), 0.01);
        assertEquals("НЕ ПРОМАХ", resultMeasurement.getMaxMiss());
        assertEquals("НЕ ПРОМАХ", resultMeasurement.getMinMiss());
    }
}