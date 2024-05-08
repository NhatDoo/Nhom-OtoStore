package DAO;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class GiaoDichDAO {
    Connection con;
    Statement stmt;
    int id;
    int idXe;
    String loaiGiaoDich;
    Date ngayGiaoDich;
    double giaTien;

        public GiaoDichDAO() {
        }

        public void openConnection() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
        }

        private GiaoDichDAO Parameter(HttpServletRequest request) {
            GiaoDichDAO giaoDich = new GiaoDichDAO();

            try {
                giaoDich.id = Integer.parseInt(request.getParameter("id"));
                giaoDich.idXe = Integer.parseInt(request.getParameter("id_xe"));
                giaoDich.loaiGiaoDich = request.getParameter("loai_giao_dich");
                giaoDich.ngayGiaoDich = Date.valueOf(request.getParameter("ngay_giao_dich"));
                giaoDich.giaTien = Double.parseDouble(request.getParameter("gia_tien"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return giaoDich;
        }

           public void displayData(PrintWriter out) {
            try {
                openConnection();
                stmt = con.createStatement();
                String sql = "SELECT * FROM giao_dich";
                ResultSet rs = stmt.executeQuery(sql);
                out.println("<table class='custom-table'>");
                out.println("<tr><th>ID</th><th>ID Xe</th><th>Loại Giao Dịch</th><th>Ngày Giao Dịch</th><th>Giá Tiền</th></tr>");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idXe = rs.getInt("id_xe");
                    String loaiGiaoDich = rs.getString("loai_giao_dich");
                    Date ngayGiaoDich = rs.getDate("ngay_giao_dich");
                    double giaTien = rs.getDouble("gia_tien");
                    out.println("<tr><td>" + id + "</td><td>" + idXe + "</td><td>" + loaiGiaoDich + "</td><td>" + ngayGiaoDich + "</td><td>" + giaTien + "</td></td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maGiaoDichXoa' value='" + id + "'>" +
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
            public void nhapGiaoDich(HttpServletRequest request, HttpServletResponse response) {
            try {
                openConnection();
                GiaoDichDAO giaoDichDAO = Parameter(request);
                String sql = "INSERT INTO giao_dich (id ,id_xe, loai_giao_dich, ngay_giao_dich, gia_tien) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setInt(1, giaoDichDAO.id);
                    statement.setInt(2, giaoDichDAO.idXe);
                    statement.setString(3, giaoDichDAO.loaiGiaoDich);
                    statement.setDate(4, giaoDichDAO.ngayGiaoDich);
                    statement.setDouble(5, giaoDichDAO.giaTien);

                    int rowsInserted = statement.executeUpdate();
                    PrintWriter out = response.getWriter();
                    if (rowsInserted > 0) {
                        out.println("Dữ liệu giao dịch đã được chèn thành công!");
                    } else {
                        out.println("Không thể chèn dữ liệu giao dịch!");
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
            String maKhachHangParam = request.getParameter("maGiaoDichXoa");
            if (maKhachHangParam != null && !maKhachHangParam.isEmpty()) {
                int maKhachHang = Integer.parseInt(maKhachHangParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("giao_dich", "id", maKhachHang);

                    con.commit();

                    out.println("Xóa giao dịch thành công!");
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
    public void searchGiaoDich(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchId = req.getParameter("searchMaGiaoDich");

    try {
        openConnection();
        String sql = "SELECT * FROM giao_dich WHERE id=?";
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
                out.println("<tr><th>ID</th><th>ID Xe</th><th>Loại Giao Dịch</th><th>Ngày Giao Dịch</th><th>Giá Tiền</th></tr>");
                out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("id_xe") + "</td><td>" + resultSet.getString("loai_giao_dich") + "</td><td>" + resultSet.getString("ngay_giao_dich") + "</td><td>" + resultSet.getString("gia_tien") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maGiaoDichXoa' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                out.println("</table>");
            } else {
                out.println("Không tìm thấy dữ liệu cho ID Giao Dịch: " + searchId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
    public void suaGiaoDich(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    res.setContentType("text/html");
    boolean dataUpdated = false;
    GiaoDichDAO gd = Parameter(req);
    try {
        openConnection();
        String sql = "UPDATE giao_dich SET id_xe=?, loai_giao_dich=?, ngay_giao_dich=?, gia_tien=? WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, gd.idXe);
            statement.setString(2, gd.loaiGiaoDich);
            statement.setDate(3, gd.ngayGiaoDich);
            statement.setDouble(4, gd.giaTien);
            statement.setInt(5, gd.id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                dataUpdated = true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }

    if (dataUpdated) {
        out.println("Dữ liệu giao dịch đã được cập nhật thành công!");
    } else {
        out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng ID Giao Dịch tồn tại.");
    }
}

    

}
