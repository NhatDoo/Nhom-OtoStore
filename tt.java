
import DAO.XulianhDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/tt"})
public class tt extends HttpServlet {
    XulianhDAO xe = new XulianhDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                    res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Khách Hàng</title>");
        out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
        out.println("</head>");
        out.println("<div class='content'>");
        out.println("<body>");
        menu mn = new menu();
        mn.htmenu1(res, out);
        out.println("<h2>Nhập thông tin khách hàng</h2>");
        out.println("<form class='form-container' method='get'>");
       
        out.println("  <label for='thang'>Tháng:</label>");
        out.println("  <input type='text' id='thang' name='thang' required><br>");
        
        out.println("  <label for='nam'>Năm:</label>");
        out.println("  <input type='text' id='nam' name='nam' required><br>");
        
        out.println("  <input type='submit' value='Kết quả'>");
        out.println("</form>");
        String thang = req.getParameter("thang");
        String nam = req.getParameter("nam");
        if(thang != null && nam !=null)
        {
        xe.displayData2(out, req,thang,nam);
        }
        

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
