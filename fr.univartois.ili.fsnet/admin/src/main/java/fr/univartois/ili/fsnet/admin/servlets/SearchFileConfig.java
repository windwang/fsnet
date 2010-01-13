package fr.univartois.ili.fsnet.admin.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchFileConfig extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    public static final String FILE_PATH = System.getProperty("user.home")
            + "/.fsnetadminrc";
    
    public static final String HOME = "/index.jsp?accueil=current";
    
    public static final String OPTIONS = "/optionsrequired.jsp?options=current";

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String redirection = searchFile();
        RequestDispatcher disp = getServletContext().getRequestDispatcher(
                redirection);
        disp.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String searchFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return OPTIONS;
        }
        return HOME;
    }
}
