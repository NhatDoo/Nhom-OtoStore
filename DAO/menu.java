
package DAO;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {   
        out.println("<div class='menu'>");
        out.println("<a href='XemKhachHang' style='text-decoration: none; color: #333;'>Yêu cầu đặt cọc</a>");
        out.println("<a href='XemDonHang' style='text-decoration: none; color: #333;'>Đơn hàng</a>");
        out.println("<a href='XemChiTietDonHang' style='text-decoration: none; color: #333;'>Chi tiết đơn hàng</a>");
        out.println("<a href='muahangdangxuat' style='text-decoration: none; color: #333;'>Giao diện</a>");
        out.println("<a href='XemThanhToan' style='text-decoration: none; color: #333;'>Chi tiết đơn hàng</a>");
        out.println("<a href='tt' style='text-decoration: none; color: #333;'>Tổng số tiền</a>");
        out.println("</div>");
    }
        public void htmenu1(HttpServletResponse res,PrintWriter out)
    {   
        out.println("<div class='menu'>");
        out.println("<a href='XemXeOto' style='text-decoration: none; color: #333;'>Ô tô</a>");
        out.println("<a href='muahangdangxuat' style='text-decoration: none; color: #333;'>Giao diện</a>");
        out.println("</div>");
        
    }
          public void htmenu2(HttpServletResponse res,PrintWriter out)
    {   
        out.println("<div class='menu'>");
        out.println("<a href='XemNhanVien' style='text-decoration: none; color: #333;'>Nhân viên</a>");
        out.println("<a href='muahangdangxuat' style='text-decoration: none; color: #333;'> Giao diện </a>");
        out.println("</div>");
    }
}
