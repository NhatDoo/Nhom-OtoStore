package DAO;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class DonHangDAO {
    Connection con;
    Statement stmt;
    int id;
    int idKhachHang;
    Date ngayDat;

    public DonHangDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }

    private DonHangDAO Parameter(HttpServletRequest request) {
        DonHangDAO donHang = new DonHangDAO();

        try {
            donHang.id = Integer.parseInt(request.getParameter("id"));
            donHang.idKhachHang = Integer.parseInt(request.getParameter("id_khach_hang"));
            donHang.ngayDat = Date.valueOf(request.getParameter("ngay_dat"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return donHang;
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM don_hang";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class='custom-table'>");
            out.println("<tr><th>ID</th><th>ID Khách Hàng</th><th>Ngày Đặt</th></tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                int idKhachHang = rs.getInt("id_khach_hang");
                Date ngayDat = rs.getDate("ngay_dat");
                out.println("<tr><td>" + id + "</td><td>" + idKhachHang + "</td><td>" + ngayDat + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maDonHangXoa' value='" + id + "'>" +
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

    public void suaDonHang(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        DonHangDAO dh = Parameter(req);
        try {
            openConnection();
            String sql = "UPDATE don_hang SET id_khach_hang=?, ngay_dat=? WHERE id=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, dh.idKhachHang);
                statement.setDate(2, dh.ngayDat);
                statement.setInt(3, dh.id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("Dữ liệu đơn hàng đã được cập nhật thành công!");
                } else {
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng ID Đơn Hàng tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void nhapDonHang(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        DonHangDAO donHangDAO = Parameter(request);
        String sql = "INSERT INTO don_hang (id,id_khach_hang, ngay_dat) VALUES (?,?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, donHangDAO.id);
            statement.setInt(2, donHangDAO.idKhachHang);
            statement.setDate(3, donHangDAO.ngayDat);

            int rowsInserted = statement.executeUpdate();
            PrintWriter out = response.getWriter();
            if (rowsInserted > 0) {
                out.println("Dữ liệu đơn hàng đã được chèn thành công!");
            } else {
                out.println("Không thể chèn dữ liệu đơn hàng!");
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
            String maKhachHangParam = request.getParameter("maDonHangXoa");
            if (maKhachHangParam != null && !maKhachHangParam.isEmpty()) {
                int maKhachHang = Integer.parseInt(maKhachHangParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("don_hang", "id", maKhachHang);

                    con.commit();

                    out.println("Xóa đơn hàng thành công!");
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
    public void searchDonHang(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchId = req.getParameter("searchMaDonHang");

    try {
        openConnection();
        String sql = "SELECT * FROM don_hang WHERE id=?";
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
                out.println("<tr><th>ID</th><th>ID Khách Hàng</th><th>Ngày Đặt</th></tr>");
                out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("id_khach_hang") + "</td><td>" + resultSet.getString("ngay_dat") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maDonHangXoa' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                out.println("</table>");
            } else {
                out.println("Không tìm thấy dữ liệu cho ID Đơn Hàng: " + searchId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}

}

