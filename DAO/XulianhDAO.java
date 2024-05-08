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

@MultipartConfig
public class XulianhDAO {
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
    double ggd;
    
    public XulianhDAO() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/khooto?useUnicode=true&characterEncoding=UTF-8", "root", "Nhat123456789");
    }
    private XulianhDAO Parameter(HttpServletRequest request) {
        XulianhDAO oto = new XulianhDAO();

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
        oto.hinh = request.getParameter("GGD");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return oto;
    }
    
    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT hinh , id FROM xe_oto";
            ResultSet rs = stmt.executeQuery(sql);
           
            out.println("<table class='container'>");
            while (rs.next()) {
 
                String hinh = rs.getString("hinh");
                int id = rs.getInt("id");
                out.println("<td>"+"<div style='position: relative;'>"+"<img src='NewServlet?hinh=" + hinh + "' width='200' height='200'>"+"<button class='button-6' style='position: absolute; bottom: 10px; left"
                        + ": 10px;' onclick=\"window.location.href='Chitiet?id="+id+"'\"'>Đặt cọc ngay</button>"+"</div>"+"</td>");
            }
            out.println("</table>");
           
    
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
        
    }
        public void displayData2(PrintWriter out,HttpServletRequest request , String thang , String nam) {
            try {
            openConnection();
            stmt = con.createStatement();
            String sql = "SELECT SUM(chi_tiet_don_hang.so_luong * xe_oto.GDD) AS TongTien " +
                     "FROM chi_tiet_don_hang " +
                     "JOIN xe_oto ON chi_tiet_don_hang.id_xe_oto = xe_oto.id " +
                     "JOIN don_hang ON chi_tiet_don_hang.id_don_hang = don_hang.id " +
                     "AND YEAR(don_hang.ngay_dat) = "+nam+" WHERE MONTH(don_hang.ngay_dat)= "+thang+"";      
            PreparedStatement pstmt = con.prepareStatement(sql);
          
            ResultSet rs = pstmt.executeQuery(sql);
            
            while (rs.next()) {
 
                long tt = rs.getLong("TongTien");
                
                out.println("<td>Tổng tiền tháng "+thang+" năm "+nam+" :"+tt+" VND</td>");
            }
           
    
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
        
    }
      public void displayData1(PrintWriter out, String id1) {
       
        try {
        openConnection();
        String sql = "SELECT * FROM xe_oto WHERE id=?";
        
        // Tạo câu lệnh PreparedStatement với tham số
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, id1); 
        
        ResultSet rs = pstmt.executeQuery();
        out.println("<table class='container'>");
        while (rs.next()) {
            String hinh = rs.getString("hinh");
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
            
            out.println("<td>" + 
                            "<div style='position: relative;'>" + 
                                "<img src='NewServlet?hinh=" + hinh + "' width='500' height='500'>" + 
                            "</div>" + 
                        "</tr>"
                         +"<td>"+"Hãng xe :"+hangXe+"</tr>"+"<td>"+"Mẫu xe :"+mauXe+"</tr>"+"<td>"+" Năm sản xuất :"+namSanXuat+"</tr>"+"<td>"+"Màu sơn:"+mauSon+"</tr>"
                         +"<td>"+"Dung tích động cơ :"+dungTichDongCo+"</tr>"+"<td>"+"Chạy bằng nhiên liệu :"+loaiNhienLieu+"</tr>"+"<td>"+"Loại Hộp Số :"+loaiHopSo+"</tr>"
                         +"<td><button onclick=\"window.location.href='KhachHang'\"'>Đặt cọc ngay</button>"+"</div>"+"</td>");  
        }
        out.println("</table>");
        con.close();
    } catch (Exception e) {
        out.println("Lỗi: " + e.getMessage());
    }
}





  
}

