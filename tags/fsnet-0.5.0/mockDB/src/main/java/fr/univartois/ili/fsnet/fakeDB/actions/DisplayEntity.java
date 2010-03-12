package fr.univartois.ili.fsnet.fakeDB.actions;

import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayEntity
 */
public class DisplayEntity extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = factory.createEntityManager();
		em.close();
		Metamodel metamodel = factory.getMetamodel();
		String requestedEntity = request.getParameter("entityName");
		EntityType<?> selectedEntity = null;
		if ((metamodel != null) && (requestedEntity != null)) {
			Set<EntityType<?>> entities = metamodel.getEntities();
			for (EntityType<?> et : entities) {
				if (et.getName().equals(requestedEntity)) {
					selectedEntity = et;
				}
			}
			if (selectedEntity != null) {
				StringBuilder imgSrc = new StringBuilder(
						"http://yuml.me/diagram/class/[");
				imgSrc.append(selectedEntity.getName());
				imgSrc.append('|');
				
				int i = 0;
				for (Attribute<?, ?> attribute : selectedEntity.getDeclaredAttributes()) {
					i++;
					imgSrc.append(attribute.getName());
					imgSrc.append(" : ");
					imgSrc.append(attribute.getJavaType().getSimpleName());
					if (i < selectedEntity.getDeclaredAttributes().size()) {
						imgSrc.append(';');
					}
				}
				imgSrc.append("{bg:orange}]");
				request.setAttribute("imgsrc", imgSrc.toString());
			}
		}
		if (selectedEntity == null) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/DisplayEntities");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request
					.getRequestDispatcher("/DisplayEntity.jsp");
			rd.forward(request, response);
		}
	}

}
