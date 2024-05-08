/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.XulianhDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    
    protected void doGet(HttpServletRequest request, HttpServletResponse res)
            throws ServletException, IOException {
            String name = request.getParameter("hinh");
        String imagePath = "C:\\Users\\nhat\\Pictures\\"+name;
             
        // Đọc ảnh từ đường dẫn
        File imageFile = new File(imagePath);
        FileInputStream fis = new FileInputStream(imageFile);

        OutputStream out1 = res.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            out1.write(buffer, 0, bytesRead);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    

}
