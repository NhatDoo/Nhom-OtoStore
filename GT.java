
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhat
 */
@WebServlet(urlPatterns = {"/GT"})
public class GT extends HttpServlet {


    
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
        out.println("<link rel='stylesheet' type='text/css' href='newcss.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("</h2>Giới thiệu<h2>");
        out.println("<p>Chào mừng bạn đến với \"OtoNhom7\" - điểm đến lý tưởng cho những đam mê và mong muốn sở hữu chiếc xe ôtô hoàn hảo. Tại đây, chúng tôi tự hào là địa chỉ tin cậy của mọi tín đồ xe hơi, mang đến một bộ sưu tập đa dạng, phong phú từ các thương hiệu uy tín trên thị trường.</p>");
        out.println("<p>Với sứ mệnh mang lại sự hài lòng tối đa cho khách hàng, chúng tôi luôn chú trọng đến chất lượng sản phẩm và dịch vụ. Từ những mẫu xe sang trọng, tiện nghi cho đến các dòng xe gia đình đa dạng, chúng tôi cam kết cung cấp những lựa chọn phong phú nhất, đáp ứng mọi nhu cầu và ngân sách của khách hàng.</p>");
        out.println("<p>Đội ngũ nhân viên chuyên nghiệp và nhiệt tình sẽ luôn sẵn lòng hỗ trợ bạn trong quá trình chọn lựa và mua sắm. Chúng tôi hiểu rằng việc mua một chiếc xe ôtô không chỉ là một giao dịch mà còn là một trải nghiệm đáng nhớ. Vì vậy, mỗi khách hàng đều được đảm bảo sự tận tâm và chăm sóc đặc biệt từ chúng tôi.</p>");
        out.println("</body>");
        out.println("</html>");
    }

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    
}
