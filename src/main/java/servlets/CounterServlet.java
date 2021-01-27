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
        try {
           var intCount = Integer.parseInt(count);
           if (intCount < 3 || intCount > 30) {
               throw new IOException();
           }
            HttpSession hs = req.getSession();
            hs.setAttribute("count", count);
            resp.sendRedirect("measurements.jsp");
        } catch (Exception e) {
            req.setAttribute("error", "Введите корректное значение (от 3 до 30)");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
