import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.KhachHangDAO;
import DAO.menu;

@WebServlet("/KhachHang")
public class KhachHang extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public KhachHang() {
        super();
    }

    KhachHangDAO khachHangDAO = new KhachHangDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Khách Hàng</title>");
        out.println("<link rel='stylesheet' type='text/css' href='newcss1.css1'>");
        out.println("</head>");
        out.println("<div class='content'>");
        out.println("<body>");

        out.println("<h2>Nhập thông tin khách hàng</h2>");
        out.println("<form class='form-container' method='get'>");
        out.println("  <input type='hidden' id='id' name='id' value='1' required><br>");
        out.println("  <label for='ho_ten'>Họ Tên:</label>");
        out.println("  <input type='text' id='ho_ten' name='ho_ten' required><br>");
        out.println("  <label for='dia_chi'>Địa Chỉ:</label>");
        out.println("  <input type='text' id='dia_chi' name='dia_chi' required><br>");
        out.println("  <label for='so_dien_thoai'>Số Điện Thoại:</label>");
        out.println("  <input type='text' id='so_dien_thoai' name='so_dien_thoai' required><br>");
        out.println("  <label for='email'>email:</label>");
        out.println("  <input type='text' id='email' name='email' required><br>");
        out.println("  <input type='submit' value='Thêm Khách Hàng'>");
        out.println("</form>");

        

        out.println("</body>");
        out.println("</html>");
        
        String hoTen = req.getParameter("ho_ten");
        String diaChi = req.getParameter("dia_chi");
        String soDienThoai = req.getParameter("so_dien_thoai");
        String mail = req.getParameter("email");
        if(hoTen!= null && diaChi != null && soDienThoai !=null && mail != null )
        {
           khachHangDAO.nhap(req, res);
           res.sendRedirect(req.getRequestURI());
        }
        
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                res.setContentType("text/html;charset=UTF-8");
                req.setCharacterEncoding("UTF-8");
                PrintWriter out = res.getWriter();

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Form Cập Nhật Dịch Vụ</title>");
                out.println("</head>");
                out.println("<body>");
                String dv = req.getParameter("id");
                out.println("<h2>Form Cập Nhật Khách Hàng </h2>");
                out.println("<form method='post'>");
                out.println("  <label for='id'>Mã Khách Hàng:</label>");
                out.println("  <input type='hidden' id='id' name='id' value='" + dv + "'/>");
                out.println("  <label for='ho_ten'>Họ Tên:</label>");
                out.println("  <input type='text' id='ho_ten' name='ho_ten' required><br>");
                out.println("  <label for='dia_chi'>Địa Chỉ:</label>");
                out.println("  <input type='text' id='dia_chi' name='dia_chi' required><br>");
                out.println("  <label for='so_dien_thoai'>Số Điện Thoại:</label>");
                out.println("  <input type='text' id='so_dien_thoai' name='so_dien_thoai' required><br>");
                out.println("  <label for='email'>email:</label>");
                out.println("  <input type='text' id='email' name='email' required><br>");
                out.println("  <input type='submit' value='Thêm Khách Hàng'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
                
                String hoTen = req.getParameter("ho_ten");
                String diaChi = req.getParameter("dia_chi");
                String soDienThoai = req.getParameter("so_dien_thoai");
                String mail = req.getParameter("email");
                
                if(hoTen!= null && diaChi != null && soDienThoai !=null && mail != null )
                {
                 khachHangDAO.suaKhachHang(req, res, out);
                }
                
        }
       
}
