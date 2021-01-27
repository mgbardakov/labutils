package servlets;

import calculator.Calculator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MeasurementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
           var data = new double[Integer.parseInt((String)req.getSession().getAttribute("count"))];
           for (int i = 0; i < data.length; i++) {
               var stringMeas = req.getParameter(String.format("count%s", i + 1));
               stringMeas = stringMeas.replace(",", ".");
               data[i] = Double.parseDouble(stringMeas);
           }
           var absString = req.getParameter("miss");
           var isAbsolute = absString.equals("true");
           var stringMiss = req.getParameter("missValue");
           stringMiss = stringMiss.replace(",", ".");
           var missValue = Double.parseDouble(stringMiss);
           var calculator = new Calculator(data, missValue, isAbsolute);
           var statistics = calculator.getMeasurement();
           req.setAttribute("stats", statistics);
           req.getRequestDispatcher("statistics.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Введите корректные значения");
            req.getRequestDispatcher("measurements.jsp").forward(req, resp);
        }
    }
}
