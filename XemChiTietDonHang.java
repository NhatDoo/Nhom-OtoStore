import DAO.ChiTietDonHangDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/XemChiTietDonHang")
public class XemChiTietDonHang extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Chi Tiết Đơn Hàng</title>");
       
        
         out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='content'>");
        menu mn = new menu();
        mn.htmenu(res, out);
        out.println("<h2>Tìm Kiếm Chi Tiết Đơn Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaChiTietDonHang'>Mã Chi Tiết Đơn Hàng:</label>");
        out.println("  <input type='text' id='searchMaChiTietDonHang' name='searchMaChiTietDonHang' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
       
        // Form thêm mới chi tiết đơn hàng
        out.println("<h2>Thêm Chi Tiết Đơn Hàng Mới</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='id'>ID:</label>");
        out.println("  <input type='text' id='id' name='id' required><br>");
        out.println("  <label for='id_don_hang'>Mã Đơn Hàng:</label>");
        out.println("  <input type='text' id='id_don_hang' name='id_don_hang' required><br>");
        out.println("  <label for='id_xe_oto'>Mã Xe Ô Tô:</label>");
        out.println("  <input type='text' id='id_xe_oto' name='id_xe_oto' required><br>");
        out.println("  <label for='so_luong'>Số Lượng:</label>");
        out.println("  <input type='text' id='so_luong' name='so_luong' required><br>");
        out.println("  <input type='submit' value='Thêm Chi Tiết Đơn Hàng'>");
        out.println(" <input type='reset' value=\"Reset\">");
        out.println("</form>");
        out.println("<h2>Thông Tin Chi Tiết Đơn Hàng</h2>");

        String TK = req.getParameter("searchMaChiTietDonHang");
        if (TK == null) {
            chiTietDonHangDAO.displayData(out);
        } else {
            chiTietDonHangDAO.searchChiTietDonHang(req, res, out);
        }
        String idDonHang = req.getParameter("id_don_hang");
        String idXeOto = req.getParameter("id_xe_oto");
        String soLuong = req.getParameter("so_luong");

        if (idDonHang != null && idXeOto != null && soLuong != null) {
            chiTietDonHangDAO.nhapChiTietDonHang(req, res);
            res.sendRedirect(req.getRequestURI());
        }
        chiTietDonHangDAO.xoa(req, res, out);
        out.println("</body>");
        out.println("</div>");
        out.println("</html>");
        
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Cập Nhật Chi Tiết Đơn Hàng</title>");
        out.println("</head>");
        out.println("<body>");
        String idChiTietDonHang = req.getParameter("id");
        out.println("<h2>Form Cập Nhật Chi Tiết Đơn Hàng </h2>");
        out.println("<form method='post'>");
        out.println("  <input type='hidden' id='id' name='id' value='" + idChiTietDonHang + "'/>");
        out.println("  <label for='id_don_hang'>Mã Đơn Hàng:</label>");
        out.println("  <input type='text' id='id_don_hang' name='id_don_hang' required><br>");
        out.println("  <label for='id_xe_oto'>Mã Xe Ô Tô:</label>");
        out.println("  <input type='text' id='id_xe_oto' name='id_xe_oto' required><br>");
        out.println("  <label for='so_luong'>Số Lượng:</label>");
        out.println("  <input type='text' id='so_luong' name='so_luong' required><br>");
        out.println("  <input type='submit' value='Cập Nhật Chi Tiết Đơn Hàng'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        String idDonHang = req.getParameter("id_don_hang");
        String idXeOto = req.getParameter("id_xe_oto");
        String soLuong = req.getParameter("so_luong");

        if (idDonHang != null && idXeOto != null && soLuong != null) {
            chiTietDonHangDAO.suaChiTietDonHang(req, res, out);
            out.println("cập nhập thành công");
        }
    }
}
