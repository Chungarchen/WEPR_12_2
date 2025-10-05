package murach.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import murach.business.User;
import murach.data.UserDB;

@WebServlet("/userAdmin")  // mapping servlet
public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String url = "/index.jsp";

        // lấy action từ request
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "display_users"; // mặc định
        }

        switch (action) {
            case "display_users": {
                List<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/index.jsp";
                break;
            }

            case "display_user": {
                String email = request.getParameter("email");
                User user = UserDB.selectUser(email);
                request.setAttribute("user", user);
                url = "/user.jsp";
                break;
            }

            case "update_user": {
                String email = request.getParameter("email");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");

                User user = new User(email, firstName, lastName);
                UserDB.update(user);

                List<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/index.jsp";
                break;
            }

            case "delete_user": {
                String email = request.getParameter("email");
                User user = UserDB.selectUser(email);
                if (user != null) {
                    UserDB.delete(user);
                }

                List<User> users = UserDB.selectUsers();
                request.setAttribute("users", users);
                url = "/index.jsp";
                break;
            }

            default:
                url = "/index.jsp";
                break;
        }

        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {
        doPost(request, response);
    }
}
