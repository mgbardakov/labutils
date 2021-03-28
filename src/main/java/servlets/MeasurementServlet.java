package servlets;

import service.JSONUncertaintyService;
import service.UncertaintyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class MeasurementServlet extends HttpServlet {

    private final UncertaintyService service = new JSONUncertaintyService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        var inputData = req.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        var message = service.getUncertainty(inputData);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(),
                true, StandardCharsets.UTF_8);
        writer.print(message);
        writer.flush();
    }
}
