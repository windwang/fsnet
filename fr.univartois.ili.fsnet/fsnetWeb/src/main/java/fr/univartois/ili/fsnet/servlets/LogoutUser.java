package fr.univartois.ili.fsnet.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutUser
 * 
 * @author lionel.desruelles
 */
public class LogoutUser extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see Servlet#destroy()
     */
    public void destroy() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {

        HttpSession session;
        session = request.getSession();
        session.invalidate();

        RequestDispatcher dis;
        dis = getServletContext().getRequestDispatcher("/login.jsp");
        dis.forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }
}
