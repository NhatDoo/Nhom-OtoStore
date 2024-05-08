import DAO.XeOtoDAO;
import DAO.menu;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/XemXeOto")
public class XemXeOto extends HttpServlet {
    private static final long serialVersionUID = 1L;
    XeOtoDAO dao = new XeOtoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Xem Danh Sách Xe Ô Tô</title>");
            out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='content'>");
            menu mn = new menu();
            mn.htmenu1(response, out);
            out.println("<h2>Tìm Kiếm Xe Ô Tô</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='searchId'>ID Xe:</label>");
            out.println("  <input type='text' id='searchId' name='searchId' required>");
            out.println("  <input type='submit' value='Tìm Kiếm'>");
            out.println("</form>");

            out.println("<h2>Form Nhập Dữ Liệu Xe Ô Tô</h2>");
            out.println("<form method='get'>");
            out.println("  <label for='id'>id:</label>");
            out.println("  <input type='text' id='id' name='id' required><br>");
            out.println("  <label for='HangXe'>Hãng Xe:</label>");
            out.println("  <input type='text' id='HangXe' name='HangXe' required><br>");
            out.println("  <label for='MauXe'>Màu Xe:</label>");
            out.println("  <input type='text' id='MauXe' name='MauXe' required><br>");
            out.println("  <label for='NamSanXuat'>Năm Sản Xuất:</label>");
            out.println("  <input type='text' id='NamSanXuat' name='NamSanXuat' required><br>");
            out.println("  <label for='MauSon'>Màu Sơn:</label>");
            out.println("  <input type='text' id='MauSon' name='MauSon' required><br>");
            out.println("  <label for='DungTichDongCo'>Dung Tích Động Cơ:</label>");
            out.println("  <input type='text' id='DungTichDongCo' name='DungTichDongCo' required><br>");
            out.println("  <label for='LoaiNhienLieu'>Loại Nhiên Liệu:</label>");
            out.println("  <input type='text' id='LoaiNhienLieu' name='LoaiNhienLieu' required><br>");
            out.println("  <label for='LoaiHopSo'>Loại Hộp Số:</label>");
            out.println("  <input type='text' id='LoaiHopSo' name='LoaiHopSo' required><br>");
            out.println("  <label for='SoKmDaDi'>Số Km Đã Đi:</label>");
            out.println("  <input type='text' id='SoKmDaDi' name='SoKmDaDi' required><br>");
            out.println("  <label for='NgayDangKy'>Ngày Đăng Ký:</label>");
            out.println("  <input type='text' id='NgayDangKy' name='NgayDangKy' required><br>");
            out.println("  <label for='NgayBaoDuongCuoiCung'>Ngày Bảo Dưỡng Cuối Cùng:</label>");
            out.println("  <input type='text' id='NgayBaoDuongCuoiCung' name='NgayBaoDuongCuoiCung' required><br>");
            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");
            out.println("<style>");
            out.println("body");
            out.println("table {border-collapse: collapse; width: 80%;}");
            out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
            out.println("th {background-color: #f2f2f2;}");
            out.println("</style>");
           
         
            String tk = request.getParameter("searchId");
            if(tk == null)
            {
            dao.displayData(out);
            }
            else
            {
            dao.searchOto(request, response, out);
            }
                String hangXe = request.getParameter("HangXe");
                String mauXe = request.getParameter("MauXe");
                String namSanXuat = request.getParameter("NamSanXuat");
                String mauSon = request.getParameter("MauSon");
                String dungTichDongCo = request.getParameter("DungTichDongCo");
                String loaiNhienLieu = request.getParameter("LoaiNhienLieu");
                String loaiHopSo = request.getParameter("LoaiHopSo");
                String soKmDaDi = request.getParameter("SoKmDaDi");
                String ngayDangKy = request.getParameter("NgayDangKy");
                String ngayBaoDuongCuoiCung = request.getParameter("NgayBaoDuongCuoiCung");

                if (hangXe != null && mauXe != null) 
                {
                 dao.nhap(request, response);
                 response.sendRedirect(request.getRequestURI());
                }
            dao.xoa(request, response, out);
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
                out.println("<title>Form Cập Nhật Xe Ô Tô</title>");
                out.println("<link rel='stylesheet' type='text/css' href='newcss2.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='content'>");
                String idXe = req.getParameter("id");
                out.println("<h2>Form Cập Nhật Xe Ô Tô</h2>");
                out.println("<form method='post'>");
                
                out.println("  <input type='hidden' id='id' name='id' value='" + idXe + "'/>");
                out.println("  <label for='HangXe'>Hãng Xe:</label>");
                out.println("  <input type='text' id='HangXe' name='HangXe' required><br>");
                out.println("  <label for='MauXe'>Mẫu Xe:</label>");
                out.println("  <input type='text' id='MauXe' name='MauXe' required><br>");
                out.println("  <label for='NamSanXuat'>Năm Sản Xuất:</label>");
                out.println("  <input type='text' id='NamSanXuat' name='NamSanXuat' required><br>");
                out.println("  <label for='MauSon'>Màu Sơn:</label>");
                out.println("  <input type='text' id='MauSon' name='MauSon' required><br>");
                out.println("  <label for='DungTichDongCo'>Dung Tích Động Cơ:</label>");
                out.println("  <input type='text' id='DungTichDongCo' name='DungTichDongCo' required><br>");
                out.println("  <label for='LoaiNhienLieu'>Loại Nhiên Liệu:</label>");
                out.println("  <input type='text' id='loaiNhienLieu' name='LoaiNhienLieu' required><br>");
                out.println("  <label for='LoaiHopSo'>Loại Hộp Số:</label>");
                out.println("  <input type='text' id='LoaiHopSo' name='LoaiHopSo' required><br>");
                out.println("  <label for='SoKmDaDi'>Số Km Đã Đi:</label>");
                out.println("  <input type='text' id='SoKmDaDi' name='SoKmDaDi' required><br>");
                out.println("  <label for='NgayDangKy'>Ngày Đăng Ký:</label>");
                out.println("  <input type='text' id='NgayDangKy' name='NgayDangKy' required><br>");
                out.println("  <label for='NgayBaoDuongCuoiCung'>Ngày Bảo Dưỡng Cuối Cùng:</label>");
                out.println("  <input type='text' id='NgayBaoDuongCuoiCung' name='NgayBaoDuongCuoiCung' required><br>");
                out.println("  <label for='GDD'>Giá bán:</label>");
                out.println("  <input type='text' id='GDD' name='GDD' required><br>");
                out.println("  <label for='hinh'>Hình:</label>");
                out.println("  <input type='file' id='hinh' name='hinh' required><br>");
                out.println("  <input type='submit' value='Cập Nhật Xe Ô Tô'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");

                String hangXe = req.getParameter("HangXe");
                String mauXe = req.getParameter("MauXe");
                String namSanXuat = req.getParameter("NamSanXuat");
                String mauSon = req.getParameter("MauSon");
                String dungTichDongCo = req.getParameter("DungTichDongCo");
                String loaiNhienLieu = req.getParameter("LoaiNhienLieu");
                String loaiHopSo = req.getParameter("LoaiHopSo");
                String soKmDaDi = req.getParameter("SoKmDaDi");
                String ngayDangKy = req.getParameter("NgayDangKy");
                String ngayBaoDuongCuoiCung = req.getParameter("NgayBaoDuongCuoiCung");

                if (hangXe != null && mauXe != null) 
                {
                 dao.capNhatThongTinXeOto(req, res, out);
                }


       
    }
}
