import DAO.DonHangDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/XemDonHang")
public class XemDonHang extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DonHangDAO donHangDAO = new DonHangDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Đơn Hàng</title>");
        out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='content'>");
        menu mn = new menu();
        mn.htmenu(res, out);
        out.println("<h2>Tìm Kiếm Đơn Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaDonHang'>Mã Đơn Hàng:</label>");
        out.println("  <input type='text' id='searchMaDonHang' name='searchMaDonHang' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        // Form thêm mới đơn hàng
        out.println("<h2>Thêm Đơn Hàng Mới</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='id'>Mã Đơn Hàng:</label>");
        out.println("  <input type='text' id='id' name='id' required><br>");
        out.println("  <label for='id_khach_hang'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='id_khach_hang' name='id_khach_hang' required><br>");
        out.println("  <label for='ngay_dat'>Ngày Đặt:</label>");
        out.println("  <input type='text' id='ngay_dat' name='ngay_dat' required><br>");
        out.println("  <input type='submit' value='Thêm Đơn Hàng'>");
        out.println("</form>");
        out.println("<h2>Thông Tin Đơn Hàng</h2>");

        String TK = req.getParameter("searchMaDonHang");
        if (TK == null) {
            donHangDAO.displayData(out);
        } else {
            donHangDAO.searchDonHang(req, res, out);
        }
       String idKhachHang = req.getParameter("id_khach_hang");
       String ngayDat = req.getParameter("ngay_dat");

    if (idKhachHang != null && ngayDat != null) {
        donHangDAO.nhapDonHang(req, res);
        res.sendRedirect(req.getRequestURI());
    }
        donHangDAO.xoa(req, res, out);

        out.println("</body>");
        out.println("</div>");
        out.println("</html>");
        
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    req.setCharacterEncoding("UTF-8");
    PrintWriter out = res.getWriter();

    out.println("<html>");
    out.println("<head>");
    out.println("<title>Form Cập Nhật Đơn Hàng</title>");
    out.println("</head>");
    out.println("<body>");
    String idDonHang = req.getParameter("id");
    out.println("<h2>Form Cập Nhật Đơn Hàng </h2>");
    out.println("<form method='post'>");
    out.println("  <input type='hidden' id='id' name='id' value='" + idDonHang + "'/>");
    out.println("  <label for='id_khach_hang'>Mã Khách Hàng:</label>");
    out.println("  <input type='text' id='id_khach_hang' name='id_khach_hang' required><br>");
    out.println("  <label for='ngay_dat'>Ngày Đặt:</label>");
    out.println("  <input type='text' id='ngay_dat' name='ngay_dat' required><br>");
    out.println("  <input type='submit' value='Cập Nhật Đơn Hàng'>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
    String idKhachHang = req.getParameter("id_khach_hang");
    String ngayDat = req.getParameter("ngay_dat");

    if (idKhachHang != null && ngayDat != null) {
        donHangDAO.suaDonHang(req, res, out);
    }
}

}
