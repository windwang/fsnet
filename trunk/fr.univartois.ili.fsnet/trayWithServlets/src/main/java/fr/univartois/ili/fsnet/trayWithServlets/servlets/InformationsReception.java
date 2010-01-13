package fr.univartois.ili.fsnet.trayWithServlets.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univartois.ili.fsnet.trayDesktop.FSNetTray;

/**
 * Servlet implementation class InformationsReception
 */
public class InformationsReception extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InformationsReception() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String mesChaines = "";

        Set<String> maSet = new HashSet<String>();
        maSet = (Set<String>) request.getAttribute("maSet");
        Iterator<String> it = maSet.iterator();
        while (it.hasNext()) {
            mesChaines += "\n";
            mesChaines += it.next();
            System.out.println(mesChaines);
        }
        // ????
        //FSNetTray trayClass = new FSNetTray(mesChaines);
        //trayClass.executeTrayIcon();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}
