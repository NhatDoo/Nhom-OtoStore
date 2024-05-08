
import DAO.XulianhDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Chitiet"})
public class Chitiet extends HttpServlet {
    XulianhDAO oto = new XulianhDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            res.setContentType("text/html;charset=UTF-8");
            PrintWriter out = res.getWriter();
            req.setCharacterEncoding("UTF-8");
            res.setCharacterEncoding("UTF-8");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Mua hàng</title>");
            out.println("</head>");
            String id = req.getParameter("id");
            oto.displayData1(out, id);     
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
}
