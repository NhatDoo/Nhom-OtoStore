package DAO;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
public class ChiTietDonHangDAO {
    Connection con;
    Statement stmt;
    int id;
    int idDonHang;
    int idXeOto;
    int soLuong;

    public ChiTietDonHangDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }

    private ChiTietDonHangDAO Parameter(HttpServletRequest request) {
        ChiTietDonHangDAO chiTietDonHang = new ChiTietDonHangDAO();

        try {
            chiTietDonHang.id = Integer.parseInt(request.getParameter("id"));
            chiTietDonHang.idDonHang = Integer.parseInt(request.getParameter("id_don_hang"));
            chiTietDonHang.idXeOto = Integer.parseInt(request.getParameter("id_xe_oto"));
            chiTietDonHang.soLuong = Integer.parseInt(request.getParameter("so_luong"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return chiTietDonHang;
    }
    public void displayData4(HttpServletResponse response) throws IOException {
    try {
        openConnection();
        stmt = con.createStatement();
        String sql = "SELECT * FROM chi_tiet_don_hang";
        ResultSet rs = stmt.executeQuery(sql);

        JsonArray jsonArray = new JsonArray();
        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", rs.getInt("id"));
            jsonObject.addProperty("idDonHang", rs.getInt("id_don_hang"));
            jsonObject.addProperty("idXeOto", rs.getInt("id_xe_oto"));
            jsonObject.addProperty("soLuong", rs.getInt("so_luong"));
            jsonArray.add(jsonObject);
        }

        Gson gson = new Gson();
        String jsonData = gson.toJson(jsonArray);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonData);

        con.close();
    } catch (Exception e) {
        response.getWriter().println("Lỗi: " + e.getMessage());
    }
}

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT * FROM chi_tiet_don_hang";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class='custom-table'>");
            out.println("<tr><th>ID</th><th>ID Đơn Hàng</th><th>ID Xe Ôtô</th><th>Số Lượng</th></tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                int idDonHang = rs.getInt("id_don_hang");
                int idXeOto = rs.getInt("id_xe_oto");
                int soLuong = rs.getInt("so_luong");
                out.println("<tr><td>" + id + "</td><td>" + idDonHang + "</td><td>" + idXeOto + "</td><td>" + soLuong + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maChiTietDonHangXoa' value='" + id + "'>" +
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

    public void suaChiTietDonHang(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        ChiTietDonHangDAO ct = Parameter(req);
        try {
            openConnection();
            String sql = "UPDATE chi_tiet_don_hang SET id_don_hang=?, id_xe_oto=?, so_luong=? WHERE id=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, ct.idDonHang);
                statement.setInt(2, ct.idXeOto);
                statement.setInt(3, ct.soLuong);
                statement.setInt(4, ct.id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                } else {
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng ID Chi Tiết Đơn Hàng tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public void nhapChiTietDonHang(HttpServletRequest request, HttpServletResponse response) {
    try {
        openConnection();
        ChiTietDonHangDAO chiTietDonHangDAO = Parameter(request);
        String sql = "INSERT INTO chi_tiet_don_hang (id ,id_don_hang, id_xe_oto, so_luong) VALUES (?,?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, chiTietDonHangDAO.id);
            statement.setInt(2, chiTietDonHangDAO.idDonHang);
            statement.setInt(3, chiTietDonHangDAO.idXeOto);
            statement.setInt(4, chiTietDonHangDAO.soLuong);

            int rowsInserted = statement.executeUpdate();
            PrintWriter out = response.getWriter();
            if (rowsInserted > 0) {
                out.println("Dữ liệu chi tiết đơn hàng đã được chèn thành công!");
            } else {
                out.println("Không thể chèn dữ liệu chi tiết đơn hàng!");
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
            String maKhachHangParam = request.getParameter("maChiTietDonHangXoa");
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
    public void searchChiTietDonHang(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
    String searchId = req.getParameter("searchMaChiTietDonHang");

    try {
        openConnection();
        String sql = "SELECT * FROM chi_tiet_don_hang WHERE id=?";
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
                out.println("<tr><th>ID</th><th>ID Đơn Hàng</th><th>ID Xe Ô Tô</th><th>Số Lượng</th></tr>");
                out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("id_don_hang") + "</td><td>" + resultSet.getString("id_xe_oto") + "</td><td>" + resultSet.getString("so_luong") + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maChiTietDonHangXoa' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + resultSet.getString("id") + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
                out.println("</table>");
            } else {
                out.println("Không tìm thấy dữ liệu cho ID Chi Tiết Đơn Hàng: " + searchId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
}
    
}
