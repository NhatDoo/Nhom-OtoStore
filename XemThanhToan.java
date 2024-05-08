import DAO.ThanhToanDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/XemThanhToan")
public class XemThanhToan extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ThanhToanDAO thanhToanDAO = new ThanhToanDAO();

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


        // Form thanh toán
        out.println("<h2>Thanh Toán</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='id'>ID:</label>");
        out.println("  <input type='text' id='id' name='id' required><br>");
        out.println("  <label for='id_don_hang'>Mã Đơn Hàng:</label>");
        out.println("  <input type='text' id='id_don_hang' name='id_don_hang' required><br>");
        out.println("  <label for='id_phuong_thuc_thanh_toan'>Mã Phương Thức Thanh Toán:</label>");
        out.println("  <input type='text' id='id_phuong_thuc_thanh_toan' name='id_phuong_thuc_thanh_toan' required><br>");
        out.println("  <label for='so_tien'>Số Tiền:</label>");
        out.println("  <input type='text' id='so_tien' name='so_tien' required><br>");
        out.println("  <label for='ngay_thanh_toan'>Ngày Thanh Toán:</label>");
        out.println("  <input type='text' id='ngay_thanh_toan' name='ngay_thanh_toan' required><br>");
        out.println("  <input type='submit' value='Thanh Toán'>");
        out.println("</form>");

        out.println("<h2>Thông Tin Chi Tiết Đơn Hàng</h2>");

        String TK = req.getParameter("searchMaChiTietDonHang");
        if (TK == null) {
           thanhToanDAO.displayData(out);
        } else {
            thanhToanDAO.searchThanhToan(req, res, out);
        }
        String idDonHangThanhToan = req.getParameter("id_don_hang");
        String idPhuongThucThanhToan = req.getParameter("id_phuong_thuc_thanh_toan");
        String soTien = req.getParameter("so_tien");
        String ngayThanhToan = req.getParameter("ngay_thanh_toan");

        if (idDonHangThanhToan != null && idPhuongThucThanhToan != null && soTien != null && ngayThanhToan != null) {
            thanhToanDAO.nhapThanhToan(req, res);
            res.sendRedirect(req.getRequestURI());
        }

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
    out.println("<title>Form Cập Nhật Thanh Toán</title>");
    out.println("</head>");
    out.println("<body>");
    String idThanhToan = req.getParameter("id");
    out.println("<h2>Form Cập Nhật Thanh Toán </h2>");
    out.println("<form method='post'>");
    out.println("  <input type='hidden' id='id' name='id' value='" + idThanhToan + "'/>");
    out.println("  <label for='id_don_hang'>Mã Đơn Hàng:</label>");
    out.println("  <input type='text' id='id_don_hang' name='id_don_hang' required><br>");
    out.println("  <label for='id_phuong_thuc_thanh_toan'>Mã Phương Thức Thanh Toán:</label>");
    out.println("  <input type='text' id='id_phuong_thuc_thanh_toan' name='id_phuong_thuc_thanh_toan' required><br>");
    out.println("  <label for='so_tien'>Số Tiền:</label>");
    out.println("  <input type='text' id='so_tien' name='so_tien' required><br>");
    out.println("  <label for='ngay_thanh_toan'>Ngày Thanh Toán:</label>");
    out.println("  <input type='text' id='ngay_thanh_toan' name='ngay_thanh_toan' required><br>");
    out.println("  <input type='submit' value='Cập Nhật Thanh Toán'>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");

    String idDonHang = req.getParameter("id_don_hang");
    String idPhuongThucThanhToan = req.getParameter("id_phuong_thuc_thanh_toan");
    String soTien = req.getParameter("so_tien");
    String ngayThanhToan = req.getParameter("ngay_thanh_toan");

    // Xử lý dữ liệu cập nhật
    if (idDonHang != null && idPhuongThucThanhToan != null && soTien != null && ngayThanhToan != null) {
        thanhToanDAO.suaThanhToan(req, res, out);
        out.println("Cập nhật thành công!");
    } else {
        out.println("Vui lòng nhập đầy đủ thông tin thanh toán.");
    }
}


}

