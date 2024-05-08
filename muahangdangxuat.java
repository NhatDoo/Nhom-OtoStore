/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.XulianhDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/muahangdangxuat"})
public class muahangdangxuat extends HttpServlet {
     XulianhDAO dao = new XulianhDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Mua hàng</title>");
        out.println("<link rel='stylesheet' type='text/css' href='newcss1.css'>");
        out.println("</head>");
        
        out.println("<div class='content'>");
        out.println("<img id='header-image' src='https://www.hathanhford.com.vn/wp-content/uploads/kia-morning-2019-moi.jpg' width='200' height='100'>");
        
        out.println("<div class='menu'>");
        out.println("<a href='GT' style='text-decoration: none; color: #333;'>Giới thiệu</a>");
        out.println("<a href='https://vnexpress.net/oto-xe-may/v-car' style='text-decoration: none; color: #333;'>Tin tức</a>");
        out.println("<a href='muahang' style='text-decoration: none; color: #333;'>Đăng xuất</a>");
        out.println("</div>");
        
       
        
        dao.displayData(out);
        out.println("</div>");
        
        out.println("<footer>");
        out.println("<p>&copy; 2024 My Website</p>");
        out.println("</footer>");
        out.println("</html>");
        
         
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


}
