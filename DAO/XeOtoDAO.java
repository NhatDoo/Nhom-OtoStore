package DAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.math.BigInteger;

@MultipartConfig
public class XeOtoDAO {
    Connection con;
    Statement stmt;
    int id;
    String hangXe;
    String mauXe;
    int namSanXuat;
    String mauSon;
    double dungTichDongCo;
    String loaiNhienLieu;
    String loaiHopSo;
    int soKmDaDi;
    Date ngayDangKy;
    Date ngayBaoDuongCuoiCung;
    String hinh;
    long GGD;
    
    public XeOtoDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
    private XeOtoDAO Parameter(HttpServletRequest request) {
        XeOtoDAO oto = new XeOtoDAO();

        try {
        oto.id = Integer.parseInt(request.getParameter("id"));
        oto.hangXe = request.getParameter("HangXe");
        oto.mauXe = request.getParameter("MauXe");
        oto.namSanXuat = Integer.parseInt(request.getParameter("NamSanXuat"));
        oto.mauSon = request.getParameter("MauSon");
        oto.dungTichDongCo = Double.parseDouble(request.getParameter("DungTichDongCo"));
        oto.loaiNhienLieu = request.getParameter("LoaiNhienLieu");
        oto.loaiHopSo = request.getParameter("LoaiHopSo");
        oto.soKmDaDi = Integer.parseInt(request.getParameter("SoKmDaDi"));
        oto.ngayDangKy = Date.valueOf(request.getParameter("NgayDangKy"));
        oto.ngayBaoDuongCuoiCung = Date.valueOf(request.getParameter("NgayBaoDuongCuoiCung"));
        oto.hinh = request.getParameter("hinh");
        oto.GGD = Long.parseLong(request.getParameter("GDD"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return oto;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM xe_oto";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class='custom-table'>");
            out.println("<tr><th>ID</th><th>Hãng Xe</th><th>Màu Xe</th><th>Năm Sản Xuất</th><th>Màu Sơn</th><th>Dung Tích Động Cơ</th><th>Loại Nhiên Liệu</th><th>Loại Hộp Số</th><th>Số Km Đã Đi</th><th>Ngày Đăng Ký</th><th>Ngày Bảo Dưỡng Cuối Cùng</th></th><th>Hình</th><th>Giá bán (VND)</th><tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                String hangXe = rs.getString("hang_xe");
                String mauXe = rs.getString("mau_xe");
                int namSanXuat = rs.getInt("nam_san_xuat");
                String mauSon = rs.getString("mau_son");
                double dungTichDongCo = rs.getDouble("dung_tich_dong_co");
                String loaiNhienLieu = rs.getString("loai_nhien_lieu");
                String loaiHopSo = rs.getString("loai_hop_so");
                int soKmDaDi = rs.getInt("so_km_da_di");
                Date ngayDangKy = rs.getDate("ngay_dang_ky");
                Date ngayBaoDuongCuoiCung = rs.getDate("ngay_bao_duong_cuoi_cung");
                String hinh = rs.getString("hinh");
                long ggd = rs.getLong("GDD");
                out.println("<tr><td>" + id + "</td><td>" + hangXe + "</td><td>" + mauXe + "</td><td>" + namSanXuat + "</td><td>" + mauSon + "</td><td>" + dungTichDongCo + "</td><td>" + loaiNhienLieu + "</td><td>" + loaiHopSo + "</td><td>" + soKmDaDi + "</td><td>" + ngayDangKy + "</td><td>" + ngayBaoDuongCuoiCung + "</td><td><img src='NewServlet?hinh=" + hinh + "' width='100' height='100'>"
                        +"</td><td>" + ggd + "</td><td>"+"<td><form method='get'>" +
                        "<input type='hidden' name='idXeXoa' value='" + id + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + id + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                        
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
        
    }

    public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {   
        
        try {
            openConnection();
            XeOtoDAO oto = Parameter(request);
            String sql = "INSERT INTO xe_oto (id ,hang_xe, mau_xe, nam_san_xuat, mau_son, dung_tich_dong_co, loai_nhien_lieu, loai_hop_so, so_km_da_di, ngay_dang_ky, ngay_bao_duong_cuoi_cung, hinh) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, oto.id);
                statement.setString(2, oto.hangXe);
                statement.setString(3, oto.mauXe);
                statement.setInt(4, oto.namSanXuat);
                statement.setString(5, oto.mauSon);
                statement.setDouble(6, oto.dungTichDongCo);
                statement.setString(7, oto.loaiNhienLieu);
                statement.setString(8, oto.loaiHopSo);
                statement.setInt(9, oto.soKmDaDi);
                statement.setDate(10, oto.ngayDangKy);
                statement.setDate(11, oto.ngayBaoDuongCuoiCung);
                statement.setString(12,oto.hinh);
                
                PrintWriter out = response.getWriter();
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    out.println("Dữ liệu xe đã được chèn thành công!");
                } else {
                    out.println("Không thể chèn dữ liệu xe!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoatubang(String tableName, String columnName, int value) throws Exception {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.executeUpdate();
        }
    }

    public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();

            // Retrieve the maXe parameter from the request
            String maXeParam = request.getParameter("idXeXoa");
            if (maXeParam != null && !maXeParam.isEmpty()) {
                int maXe = Integer.parseInt(maXeParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("xe_oto", "id", maXe);
                    con.commit();
                    out.println("Xoá dữ liệu xe ô tô thành công!");
                    response.sendRedirect(request.getRequestURI());
                } catch (Exception e) {
                    // Rollback the transaction if an error occurs
                    con.rollback();
                    out.println("Lỗi: " + e.getMessage());
                } finally {
                    // Reset auto-commit to true
                    con.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        } finally {
            out.close();
        }
    }
    public void capNhatThongTinXeOto(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    XeOtoDAO xeOtoDAO = Parameter(req);
    try {
        openConnection();
        String sql = "UPDATE xe_oto SET hang_xe=?, mau_xe=?, nam_san_xuat=?, mau_son=?, dung_tich_dong_co=?, loai_nhien_lieu=?, loai_hop_so=?, so_km_da_di=?, ngay_dang_ky=?, ngay_bao_duong_cuoi_cung=?, hinh=?, GDD=? WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, xeOtoDAO.hangXe);
            statement.setString(2, xeOtoDAO.mauXe);
            statement.setInt(3, xeOtoDAO.namSanXuat);
            statement.setString(4, xeOtoDAO.mauSon);
            statement.setDouble(5, xeOtoDAO.dungTichDongCo);
            statement.setString(6, xeOtoDAO.loaiNhienLieu);
            statement.setString(7, xeOtoDAO.loaiHopSo);
            statement.setInt(8, xeOtoDAO.soKmDaDi);
            statement.setDate(9, xeOtoDAO.ngayDangKy);
            statement.setDate(10, xeOtoDAO.ngayBaoDuongCuoiCung);
            statement.setString(11, xeOtoDAO.hinh);
            statement.setLong(12, xeOtoDAO.GGD);
            statement.setInt(13, xeOtoDAO.id);
           
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("Dữ liệu xe ô tô đã được cập nhật thành công!");
            } else {
                out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng ID Xe Ô Tô tồn tại.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}

   public void searchOto(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchId = req.getParameter("searchId");

    try {
        openConnection();
        String sql = "SELECT * FROM xe_oto WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Hãng Xe</th><th>Màu Xe</th><th>Năm Sản Xuất</th><th>Màu Sơn</th><th>Dung Tích Động Cơ</th><th>Loại Nhiên Liệu</th><th>Loại Hộp Số</th><th>Số Km Đã Đi</th><th>Ngày Đăng Ký</th><th>Ngày Bảo Dưỡng Cuối Cùng</th></th><th>Hình<tr>");
                out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("hang_xe") + "</td><td>" + resultSet.getString("mau_xe") + "</td><td>" + resultSet.getString("nam_san_xuat") + "</td><td>" + resultSet.getString("mau_son") + "</td><td>" 
                        + resultSet.getString("dung_tich_dong_co") + "</td><td>" + resultSet.getString("loai_nhien_lieu") + "</td><td>" + resultSet.getString("loai_hop_so") + "</td><td>" + resultSet.getString("so_km_da_di") + "</td><td>" + resultSet.getString("ngay_dang_ky") 
                        + "</td><td>" + resultSet.getString("ngay_bao_duong_cuoi_cung") + "<td><img src='NewServlet?hinh=" + resultSet.getString("hinh") + "' width='100' height='100'<td>"+"</td></tr>");
                out.println("</table>");
            } else {
                out.println("Không tìm thấy dữ liệu cho ID: " + searchId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}




  
}

