import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Thay thế các giá trị này bằng thông tin từ Google Developer Console
    private static final String CLIENT_ID = "253547586055-92ro3090ek4vojr73nsa02t80niun5nk.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/ban_oto/muahangdangxuat"; // URL của Servlet khác

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=email%20profile";
        response.sendRedirect(loginUrl);
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
