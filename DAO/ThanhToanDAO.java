package DAO;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class ThanhToanDAO {
    Connection con;
    Statement stmt;
    int id;
    int idDonHang;
    int idPhuongThucThanhToan;
    double soTien;
    Date ngayThanhToan;

    public ThanhToanDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }

    private ThanhToanDAO Parameter(HttpServletRequest request) {
        ThanhToanDAO thanhToan = new ThanhToanDAO();

        try {
            thanhToan.id = Integer.parseInt(request.getParameter("id"));
            thanhToan.idDonHang = Integer.parseInt(request.getParameter("id_don_hang"));
            thanhToan.idPhuongThucThanhToan = Integer.parseInt(request.getParameter("id_phuong_thuc_thanh_toan"));
            thanhToan.soTien = Double.parseDouble(request.getParameter("so_tien"));
            thanhToan.ngayThanhToan = Date.valueOf(request.getParameter("ngay_thanh_toan"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return thanhToan;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM thanh_toan";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class='custom-table'>");
            out.println("<tr><th>ID</th><th>ID Đơn Hàng</th><th>ID Phương Thức Thanh Toán</th><th>Số Tiền</th><th>Ngày Thanh Toán</th></tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                int idDonHang = rs.getInt("id_don_hang");
                int idPhuongThucThanhToan = rs.getInt("id_phuong_thuc_thanh_toan");
                double soTien = rs.getDouble("so_tien");
                Date ngayThanhToan = rs.getDate("ngay_thanh_toan");
                out.println("<tr><td>" + id + "</td><td>" + idDonHang + "</td><td>" + idPhuongThucThanhToan + "</td><td>" + soTien + "</td><td>" + ngayThanhToan + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maThanhToanXoa' value='" + id + "'>" +
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

    public void suaThanhToan(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        ThanhToanDAO tt = Parameter(req);
        try {
            openConnection();
            String sql = "UPDATE thanh_toan SET id_don_hang=?, id_phuong_thuc_thanh_toan=?, so_tien=?, ngay_thanh_toan=? WHERE id=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, tt.idDonHang);
                statement.setInt(2, tt.idPhuongThucThanhToan);
                statement.setDouble(3, tt.soTien);
                statement.setDate(4, tt.ngayThanhToan);
                statement.setInt(5, tt.id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                } else {
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng ID Thanh Toán tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
        public void nhapThanhToan(HttpServletRequest request, HttpServletResponse response) {
        try {
            openConnection();
            ThanhToanDAO thanhToanDAO = Parameter(request);
            String sql = "INSERT INTO thanh_toan (id,id_don_hang, id_phuong_thuc_thanh_toan, so_tien, ngay_thanh_toan) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, thanhToanDAO.id);
                statement.setInt(2, thanhToanDAO.idDonHang);
                statement.setInt(3, thanhToanDAO.idPhuongThucThanhToan);
                statement.setDouble(4, thanhToanDAO.soTien);
                statement.setDate(5, thanhToanDAO.ngayThanhToan);

                int rowsInserted = statement.executeUpdate();
                PrintWriter out = response.getWriter();
                if (rowsInserted > 0) {
                    out.println("Dữ liệu thanh toán đã được chèn thành công!");
                } else {
                    out.println("Không thể chèn dữ liệu thanh toán!");
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
            String maKhachHangParam = request.getParameter("maThanhToanXoa");
            if (maKhachHangParam != null && !maKhachHangParam.isEmpty()) {
                int maKhachHang = Integer.parseInt(maKhachHangParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("chi_tiet_don_hang", "id", maKhachHang);

                    con.commit();
                    response.sendRedirect(request.getRequestURI());
                    out.println("Xóa đơn hàng thành công!");
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
    public void searchThanhToan(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchId = req.getParameter("searchMaThanhToan");

    try {
        openConnection();
        String sql = "SELECT * FROM thanh_toan WHERE id=?";
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
                out.println("<tr><th>ID</th><th>ID Đơn Hàng</th><th>ID Phương Thức Thanh Toán</th><th>Số Tiền</th><th>Ngày Thanh Toán</th></tr>");
                out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("id_don_hang") + "</td><td>" + resultSet.getString("id_phuong_thuc_thanh_toan") + "</td><td>" + resultSet.getString("so_tien") + "</td><td>" + resultSet.getString("ngay_thanh_toan") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maThanhToanXoa' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                out.println("</table>");
            } else {
                out.println("Không tìm thấy dữ liệu cho ID Thanh Toán: " + searchId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}

}
