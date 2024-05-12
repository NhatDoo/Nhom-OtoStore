package DAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KhachHangDAO {
    Connection con;
    Statement stmt;
    int maKhachHang;
    String hoTen;
    String diaChi;
    String soDienThoai;
    String mail;

    public KhachHangDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }

    private KhachHangDAO Parameter(HttpServletRequest request) {
        KhachHangDAO khachHang = new KhachHangDAO();

        try {
            khachHang.maKhachHang = Integer.parseInt(request.getParameter("id"));
            khachHang.hoTen = request.getParameter("ho_ten");
            khachHang.diaChi = request.getParameter("dia_chi");
            khachHang.soDienThoai = request.getParameter("so_dien_thoai");
            khachHang.mail = request.getParameter("email");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return khachHang;
    }
    public void displayData4 (HttpServletResponse response) throws IOException {
    try {
        openConnection();
        stmt = con.createStatement();
        String sql = "select * from khach_hang";
        ResultSet rs = stmt.executeQuery(sql);

        JsonArray jsonArray = new JsonArray();
        while (rs.next()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("maKhachHang", rs.getInt("id"));
            jsonObject.addProperty("hoTen", rs.getString("ho_ten"));
            jsonObject.addProperty("diaChi", rs.getString("dia_chi"));
            jsonObject.addProperty("soDienThoai", rs.getString("so_dien_thoai"));
            jsonObject.addProperty("email", rs.getString("email"));
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
            String sql = "select * from khach_hang";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class='custom-table'>");
            out.println("<tr><th>Mã Khách Hàng</th><th>Họ Tên</th><th>Địa Chỉ</th><th>Số Điện Thoại</th></tr>");
            while (rs.next()) {
                int maKhachHang = rs.getInt("id");
                String hoTen = rs.getString("ho_ten");
                String diaChi = rs.getString("dia_chi");
                String soDienThoai = rs.getString("so_dien_thoai");
                String email = rs.getString("email");
                out.println("<tr><td>" + maKhachHang + "</td><td>" + hoTen + "</td><td>" + diaChi + "</td><td>" + soDienThoai + "</td>"
                        + "<td><form method='get'>" +
                        "<input type='hidden' name='maKhachHangXoa' value='" + maKhachHang + "'>" +
                        "<input type='submit' value='Xoá'>" +
                        "</form></td></td>"
                        + "<td><form method='post'>" +
                        "<input type='hidden' name='id' value='" + maKhachHang + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td></tr>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }
    private int getCurrentMaxId() throws SQLException {
    // Query the database to get the current maximum iduser
    String query = "SELECT MAX(id) AS maxId FROM khach_hang";

    try (Statement statement = con.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        if (resultSet.next()) {
            return resultSet.getInt("maxId");
        }
    }

    return 0; // Default if no records are found
}
    public void nhap(HttpServletRequest request, HttpServletResponse response) {

        try {
            openConnection();
            int currentMaxId = getCurrentMaxId();
            int idmoi = currentMaxId + 1;
        
            KhachHangDAO kh = Parameter(request);
            kh.maKhachHang = idmoi;
            
            String sql = "INSERT INTO khach_hang (id, ho_ten, dia_chi, so_dien_thoai , email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, idmoi);
                statement.setString(2, kh.hoTen);
                statement.setString(3, kh.diaChi);
                statement.setString(4, kh.soDienThoai);
                statement.setString(5, kh.mail);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Dữ liệu khách hàng đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Không thể chèn dữ liệu khách hàng!");
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
            String maKhachHangParam = request.getParameter("maKhachHangXoa");
            if (maKhachHangParam != null && !maKhachHangParam.isEmpty()) {
                int maKhachHang = Integer.parseInt(maKhachHangParam);

                // Start a transaction
                con.setAutoCommit(false);

                try {
                    xoatubang("khach_hang", "id", maKhachHang);

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

    public void searchKhachHang(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        String searchMaKhachHang = req.getParameter("searchMaKhachHang");

        try {
            openConnection();
            String sql = "SELECT * FROM khach_hang WHERE id=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaKhachHang);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Khách Hàng</th><th>Họ Tên</th><th>Địa Chỉ</th><th>Số Điện Thoại</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("id") + "</td><td>" + resultSet.getString("ho_ten") + "</td><td>" + resultSet.getString("dia_chi") + "</td><td>" + resultSet.getString("so_dien_thoai") + "</td>"
                            + "<td><form method='get'>" +
                            "<input type='hidden' name='maKhachHangXoa' value='" + resultSet.getString("id") + "'>" +
                            "<input type='submit' value='Xoá'>" +
                            "</form></td></td>"
                            + "<input type='hidden' name='id' value='" + resultSet.getString("id") + "'>" +
                            "<input type='submit' value='Sửa'>" +
                            "</form></td></tr>");
                    out.println("</table>");
                } else {
                    out.println("Không tìm thấy dữ liệu cho Mã Khách Hàng: " + searchMaKhachHang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void suaKhachHang(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        KhachHangDAO kh = Parameter(req);
        try {
            openConnection();
            String sql = "UPDATE khach_hang SET ho_ten=?, dia_chi=?, so_dien_thoai=? , email=? WHERE id=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, kh.hoTen);
                statement.setString(2, kh.diaChi);
                statement.setString(3, kh.soDienThoai);
                statement.setString(4, kh.mail);
                statement.setInt(5, kh.maKhachHang);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("Dữ liệu khách hàng đã được cập nhật thành công!");
                } else {
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Khách Hàng tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

  
}
