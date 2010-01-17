package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class CreateHub
 */
public class CreateHub extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String nom;
        String nomUTF8;
        Date date;
        date = new Date();
        Hub hub;
        nom = request.getParameter("nomHub");

        nomUTF8 = new String(nom.getBytes("ISO-8859-1"), "UTF-8");
        hub = new Hub(nomUTF8, date);
        hub.setCreateur((EntiteSociale) request.getSession().getAttribute(
                "entite"));

        IliForumFacade.getInstance().addHub(hub);
        RequestDispatcher dispa;
        dispa = getServletContext().getRequestDispatcher("/hub.jsp");
        dispa.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        this.doGet(request, response);
    }
}
