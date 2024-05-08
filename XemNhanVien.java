import DAO.NhanVienDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/XemNhanVien"})
public class XemNhanVien extends HttpServlet {
     private static final long serialVersionUID = 1L;
    NhanVienDAO nhanVienDAO = new NhanVienDAO();
   
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.setCharacterEncoding("UTF-8");
    res.setCharacterEncoding("UTF-8");
    PrintWriter out = res.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>Xem Nhân Viên</title>");
    out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='content'>");
    menu mn = new menu();
    mn.htmenu2(res, out);
    out.println("<h2>Tìm Kiếm Nhân Viên</h2>");
    out.println("<form method='get'>");
    out.println("  <label for='searchMaNhanVien'>Mã Nhân Viên:</label>");
    out.println("  <input type='text' id='searchMaNhanVien' name='searchMaNhanVien' required>");
    out.println("  <input type='submit' value='Tìm Kiếm'>");
    out.println("</form>");

    // Form thêm mới nhân viên
    out.println("<h2>Thêm Nhân Viên Mới</h2>");
    out.println("<form method='get'>");
    out.println("  <label for='id'>Mã Nhân Viên:</label>");
    out.println("  <input type='text' id='id' name='id' required><br>");
    out.println("  <label for='ho_ten'>Họ Tên:</label>");
    out.println("  <input type='text' id='ho_ten' name='ho_ten' required><br>");
    out.println("  <label for='chuc_vu'>Chức Vụ:</label>");
    out.println("  <input type='text' id='chuc_vu' name='chuc_vu' required><br>");
    out.println("  <label for='dia_chi'>Địa Chỉ:</label>");
    out.println("  <input type='text' id='dia_chi' name='dia_chi' required><br>");
    out.println("  <label for='so_dien_thoai'>Số Điện Thoại:</label>");
    out.println("  <input type='text' id='so_dien_thoai' name='so_dien_thoai' required><br>");
    out.println("  <label for='email'>Email:</label>");
    out.println("  <input type='text' id='email' name='email' required><br>");
    out.println("  <input type='submit' value='Thêm Nhân Viên'>");
    out.println("</form>");
    out.println("<h2>Thông Tin Nhân Viên</h2>");

    String TK = req.getParameter("searchMaNhanVien");
    if (TK == null) {
        nhanVienDAO.displayData(out);
    } else {
        nhanVienDAO.searchNhanVien(req, res, out);
    }
    String hoTen = req.getParameter("ho_ten");
            String diaChi = req.getParameter("dia_chi");
            String soDienThoai = req.getParameter("so_dien_thoai");
            String email = req.getParameter("email");
         
            if (hoTen != null ) {
                nhanVienDAO.nhap(req, res);
                res.sendRedirect(req.getRequestURI());
            }
    nhanVienDAO.xoa(req, res, out);
    nhanVienDAO.suaNhanVien(req, res, out);
    out.println("</div>");
}


    
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            res.setContentType("text/html;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Form Cập Nhật Nhân Viên</title>");
            out.println("</head>");
            out.println("<body>");
            String idNhanVien = req.getParameter("id");
            out.println("<h2>Form Cập Nhật Nhân Viên </h2>");
            out.println("<form method='post'>");
            out.println("  <input type='hidden' id='id' name='id' value='" + idNhanVien + "'/>");
            out.println("  <label for='ho_ten'>Họ Tên:</label>");
            out.println("  <input type='text' id='ho_ten' name='ho_ten' required><br>");
            out.println("  <label for='dia_chi'>Địa Chỉ:</label>");
            out.println("  <input type='text' id='dia_chi' name='dia_chi' required><br>");
            out.println("  <label for='chuc_vu'>Chức vụ:</label>");
            out.println("  <input type='text' id='chuc_vu' name='chuc_vu' required><br>");
            out.println("  <label for='so_dien_thoai'>Số Điện Thoại:</label>");
            out.println("  <input type='text' id='so_dien_thoai' name='so_dien_thoai' required><br>");
            out.println("  <label for='email'>Email:</label>");
            out.println("  <input type='text' id='email' name='email' required><br>");
            out.println("  <input type='submit' value='Cập Nhật Nhân Viên'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
            String hoTen = req.getParameter("ho_ten");
            String diaChi = req.getParameter("dia_chi");
            String soDienThoai = req.getParameter("so_dien_thoai");
            String email = req.getParameter("email");
         
            if (hoTen != null ) {
                nhanVienDAO.suaNhanVien(req, res, out);  
                out.println("cập nhâp thành công ");
            }

    }

}
