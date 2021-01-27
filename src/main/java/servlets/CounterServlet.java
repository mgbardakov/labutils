package servlets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CounterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var count =  req.getParameter("count");
        if (count == null || Integer.parseInt(count) < 3
                || Integer.parseInt(count) > 30) {
            req.setAttribute("error", "Введите корректное значение (от 3 до 30)");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            HttpSession hs = req.getSession();
            hs.setAttribute("count", count);
            resp.sendRedirect("measurements.jsp");
        }
    }
}
