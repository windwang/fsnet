package fr.univartois.ili.fsnet.admin.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.admin.ParserFileConfig;

/**
 * Servlet implementation class CreateFileConfig
 */
public class CreateFileConfig extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        File file = new File(SearchFileConfig.FILE_PATH);
        ParserFileConfig parser = null;
        String[] parameters = null;

        if (file.exists()) {
            parser = new ParserFileConfig(file);
            parameters = parser.parse();
            request.getSession().setAttribute("parameters", parameters);
        }
        RequestDispatcher dp = getServletContext().getRequestDispatcher(
                "/options.jsp?option=current");
        dp.forward(request, response);

    }

    private void completeFile(String smtpserver, String host, String pwdhost,
            String addressfsnet, String port, File file) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                    new FileWriter(file)));
            StringBuilder sb = new StringBuilder();
            sb.append(smtpserver);
            sb.append(" ");
            sb.append(host);
            sb.append(" ");
            sb.append(pwdhost);
            sb.append(" ");
            sb.append(addressfsnet);
            sb.append(" ");
            sb.append(port);
            writer.println(sb.toString());
            writer.close();
        } catch (IOException e) {
            log("Error", e);
        }

    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String smtpserver = request.getSession().getAttribute("serveursmtp").toString();
        String host = request.getSession().getAttribute("hote").toString();
        String pwdhost = request.getSession().getAttribute("motdepasse").toString();
        String addressfsnet = request.getSession().getAttribute("adressefsnet").toString();
        String port = request.getSession().getAttribute("port").toString();

        File file = new File(SearchFileConfig.FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        completeFile(smtpserver, host, pwdhost, addressfsnet, port, file);

        RequestDispatcher dp = getServletContext().getRequestDispatcher(
                SearchFileConfig.HOME);
        dp.forward(request, response);
    }
}
