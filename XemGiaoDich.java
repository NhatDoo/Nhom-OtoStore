
import DAO.GiaoDichDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/XemGiaoDich")
public class XemGiaoDich extends HttpServlet {
    private static final long serialVersionUID = 1L;

    GiaoDichDAO giaoDichDAO = new GiaoDichDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Giao Dịch</title>");
        out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='content'>");

        menu mn = new menu();
        mn.htmenu(res, out);
        out.println("<h2>Tìm Kiếm Giao Dịch</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaGiaoDich'>Mã Giao Dịch:</label>");
        out.println("  <input type='text' id='searchMaGiaoDich' name='searchMaGiaoDich' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        // Form thêm mới giao dịch
        out.println("<h2>Thêm Giao Dịch Mới</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='id'>Mã Giao Dịch:</label>");
        out.println("  <input type='text' id='id' name='id' required><br>");
        out.println("  <label for='id_xe'>Mã Xe:</label>");
        out.println("  <input type='text' id='id_xe' name='id_xe' required><br>");
        out.println("  <label for='loai_giao_dich'>Loại Giao Dịch:</label>");
        out.println("  <input type='text' id='loai_giao_dich' name='loai_giao_dich' required><br>");
        out.println("  <label for='ngay_giao_dich'>Ngày Giao Dịch:</label>");
        out.println("  <input type='text' id='ngay_giao_dich' name='ngay_giao_dich' required><br>");
        out.println("  <label for='gia_tien'>Giá Tiền:</label>");
        out.println("  <input type='text' id='gia_tien' name='gia_tien' required><br>");
        out.println("  <input type='submit' value='Thêm Giao Dịch'>");
        out.println("</form>");
        out.println("<h2>Thông Tin Giao Dịch</h2>");

        String TK = req.getParameter("searchMaGiaoDich");
        if (TK == null) {
            giaoDichDAO.displayData(out);
        } else {
            giaoDichDAO.searchGiaoDich(req, res, out);
        }
        String idXe = req.getParameter("id_xe");
    String loaiGiaoDich = req.getParameter("loai_giao_dich");
    String ngayGiaoDich = req.getParameter("ngay_giao_dich");
    String giaTien = req.getParameter("gia_tien");

    if (idXe != null && loaiGiaoDich != null && ngayGiaoDich != null && giaTien != null) {
        giaoDichDAO.nhapGiaoDich(req, res);
        res.sendRedirect(req.getRequestURI());
    }
        giaoDichDAO.xoa(req, res, out);

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
    out.println("<title>Form Cập Nhật Giao Dịch</title>");
    out.println("</head>");
    out.println("<body>");
    String idGiaoDich = req.getParameter("id");
    out.println("<h2>Form Cập Nhật Giao Dịch </h2>");
    out.println("<form method='post'>");
    out.println("  <input type='hidden' id='id' name='id' value='" + idGiaoDich + "'/>");
    out.println("  <label for='id_xe'>Mã Xe:</label>");
    out.println("  <input type='text' id='id_xe' name='id_xe' required><br>");
    out.println("  <label for='loai_giao_dich'>Loại Giao Dịch:</label>");
    out.println("  <input type='text' id='loai_giao_dich' name='loai_giao_dich' required><br>");
    out.println("  <label for='ngay_giao_dich'>Ngày Giao Dịch:</label>");
    out.println("  <input type='text' id='ngay_giao_dich' name='ngay_giao_dich' required><br>");
    out.println("  <label for='gia_tien'>Giá Tiền:</label>");
    out.println("  <input type='text' id='gia_tien' name='gia_tien' required><br>");
    out.println("  <input type='submit' value='Cập Nhật Giao Dịch'>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
    String idXe = req.getParameter("id_xe");
    String loaiGiaoDich = req.getParameter("loai_giao_dich");
    String ngayGiaoDich = req.getParameter("ngay_giao_dich");
    String giaTien = req.getParameter("gia_tien");

    if (idXe != null && loaiGiaoDich != null && ngayGiaoDich != null && giaTien != null) {
        giaoDichDAO.suaGiaoDich(req, res, out);
    }
}

}

       


