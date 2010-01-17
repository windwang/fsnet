package fr.univartois.ili.fsnet.facade.iliforum.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

/**
 * Servlet implementation class ModifTopic
 */
public class ModifTopic extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        String titre;
        String titreUTF8;
        Topic top;

        titre = request.getParameter("titreTopic");
        titreUTF8 = new String(titre.getBytes("ISO-8859-1"), "UTF-8");
        top = (Topic) getServletContext().getAttribute("monTopic");

        IliForumFacade.getInstance().updateTopic(top, titreUTF8);

        RequestDispatcher dispa;
        dispa = getServletContext().getRequestDispatcher("/topic.jsp");
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
