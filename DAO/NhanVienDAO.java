package DAO;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class NhanVienDAO {
    Connection con;
    Statement stmt;
    int id;
    String hoTen;
    String chucVu;
    String diaChi;
    String soDienThoai;
    String email;

    public NhanVienDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }

    private NhanVienDAO Parameter(HttpServletRequest request) {
        NhanVienDAO nhanVien = new NhanVienDAO();

        try {
            nhanVien.id = Integer.parseInt(request.getParameter("id"));
            nhanVien.hoTen = request.getParameter("ho_ten");
            nhanVien.chucVu = request.getParameter("chuc_vu");
            nhanVien.diaChi = request.getParameter("dia_chi");
            nhanVien.soDienThoai = request.getParameter("so_dien_thoai");
            nhanVien.email = request.getParameter("email");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        return nhanVien;
    }
    
    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM nhan_vien";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class='custom-table'>");
            out.println("<tr><th>ID</th><th>Họ Tên</th><th>Chức Vụ</th><th>Địa Chỉ</th><th>Số Điện Thoại</th><th>Email</th></tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                String hoTen = rs.getString("ho_ten");
                String chucVu = rs.getString("chuc_vu");
                String diaChi = rs.getString("dia_chi");
                String soDienThoai = rs.getString("so_dien_thoai");
                String email = rs.getString("email");

                out.println("<tr><td>" + id + "</td><td>" + hoTen + "</td><td>" + chucVu + "</td><td>" + diaChi + "</td><td>" + soDienThoai + "</td><td>" + email + "</td></td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maNhanVienXoa' value='" + id + "'>" +
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
        public void nhap(HttpServletRequest request, HttpServletResponse response) {
        try {
            openConnection();
            NhanVienDAO nhanVienDAO = Parameter(request);
            String sql = "INSERT INTO nhan_vien (id ,ho_ten, chuc_vu, dia_chi, so_dien_thoai, email) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, nhanVienDAO.id);
                statement.setString(2, nhanVienDAO.hoTen);
                statement.setString(3, nhanVienDAO.chucVu);
                statement.setString(4, nhanVienDAO.diaChi);
                statement.setString(5, nhanVienDAO.soDienThoai);
                statement.setString(6, nhanVienDAO.email);

                int rowsInserted = statement.executeUpdate();
                PrintWriter out = response.getWriter();
                if (rowsInserted > 0) {
                    out.println("Dữ liệu nhân viên đã được chèn thành công!");
                } else {
                    out.println("Không thể chèn dữ liệu nhân viên!");
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

            // Retrieve the maKhachHang parameter from the request
            String maKhachHangParam = request.getParameter("maNhanVienXoa");
            if (maKhachHangParam != null && !maKhachHangParam.isEmpty()) {
                int maKhachHang = Integer.parseInt(maKhachHangParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("nhan_vien", "id", maKhachHang);

                    con.commit();

                    out.println("Xóa khách hàng thành công!");
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
    public void searchNhanVien(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchId = req.getParameter("searchMaNhanVien");

    try {
        openConnection();
        String sql = "SELECT * FROM nhan_vien WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, searchId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                out.println("<style>");
                out.println("table {border-collapse: collapse; width: 80%;}");
                out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                out.println("th {background-color: #f2f2f2;}");
                out.println("</style>");
                out.println("<table %>");
                out.println("<tr><th>ID</th><th>Họ Tên</th><th>Chức Vụ</th><th>Địa Chỉ</th><th>Số Điện Thoại</th><th>Email</th></tr>");
                out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("ho_ten") + "</td><td>" + resultSet.getString("chuc_vu") + "</td><td>" + resultSet.getString("dia_chi") + "</td><td>" + resultSet.getString("so_dien_thoai") + "</td><td>" + resultSet.getString("email") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maNhanVienXoa' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + id + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                out.println("</table>");
            } else {
                out.println("Không tìm thấy dữ liệu cho ID Nhân Viên: " + searchId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
    public void suaNhanVien(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    boolean dataUpdated = false;
    NhanVienDAO nv = Parameter(req);
    try {
        openConnection();
        String sql = "UPDATE nhan_vien SET ho_ten=?, chuc_vu=?, dia_chi=?, so_dien_thoai=?, email=? WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nv.hoTen);
            statement.setString(2, nv.chucVu);
            statement.setString(3, nv.diaChi);
            statement.setString(4, nv.soDienThoai);
            statement.setString(5, nv.email);
            statement.setInt(6, nv.id);
            int rowsUpdated = statement.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}


}
