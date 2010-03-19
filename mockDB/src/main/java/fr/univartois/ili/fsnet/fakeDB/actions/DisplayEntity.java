package fr.univartois.ili.fsnet.fakeDB.actions;

import java.io.IOException;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.Type.PersistenceType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayEntity
 */
public class DisplayEntity extends HttpServlet {

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = factory.createEntityManager();
		em.close();
		Metamodel metamodel = factory.getMetamodel();
		String requestedEntity = request.getParameter("entityName");
		if ((metamodel != null) && (requestedEntity != null)) {
			EntityType<?> selectedEntity = getEntityFromClassName(metamodel,
					requestedEntity);
			String url = buildUrl(metamodel, selectedEntity);
			request.setAttribute("imgsrc", url);
		}
		RequestDispatcher rd = request
				.getRequestDispatcher("/DisplayEntity.jsp");
		rd.forward(request, response);
	}

	public static String buildUrl(Metamodel metamodel, EntityType<?> entity) {
		StringBuilder url = new StringBuilder("http://yuml.me/diagram/class/");
		IdentifiableType<?> superEntity = entity.getSupertype();
		IdentifiableType<?> subEntity = entity;
		url.append(getEntityDescription(metamodel, entity.getJavaType(), "orange"));
		url.append(", ");
		while (superEntity != null) {
			url.append(getEntityDescription(metamodel, superEntity
					.getJavaType(), null));
			url.append('^');
			url
					.append(getEntityDescription(metamodel, subEntity
							.getJavaType(), null));
			url.append(", ");
			subEntity = superEntity;
			superEntity = subEntity.getSupertype();
		}
		for (PluralAttribute<?, ?, ?> pluralAttribute : entity
				.getDeclaredPluralAttributes()) {
			url.append(getEntityDescription(metamodel, entity.getJavaType(), "orange"));
			url.append(buildRelation(pluralAttribute.getPersistentAttributeType()));
				
			url.append(getEntityDescription(metamodel, pluralAttribute
					.getElementType().getJavaType(), null));
			url.append(", ");
		}
		for (SingularAttribute<?, ?> singularAttribute : entity.getDeclaredSingularAttributes()) {
			if (singularAttribute.getType().getPersistenceType().equals(PersistenceType.ENTITY)) {
				url.append(getEntityDescription(metamodel, entity.getJavaType(), "orange"));
				url.append(buildRelation(singularAttribute.getPersistentAttributeType()));
				url.append(getEntityDescription(metamodel, singularAttribute.getType().getJavaType(), null));
				url.append(", ");
			}
		}
		return url.toString();
	}
	
	public static String buildRelation(PersistentAttributeType attributeType) {
		if (PersistentAttributeType.MANY_TO_MANY.equals(attributeType)) {
			return "<*-*>";
		} else if (PersistentAttributeType.ONE_TO_MANY.equals(attributeType)) {
			return "<1-*>";
		} else if (PersistentAttributeType.MANY_TO_ONE.equals(attributeType)) {
			return "<*-1>";
		} else if (PersistentAttributeType.ONE_TO_ONE.equals(attributeType)) {
			return "<1-1>";
		} else if (PersistentAttributeType.EMBEDDED.equals(attributeType)) {
			
		}
		return null;
	}

	public static String getEntityDescription(Metamodel metamodel,
			Class<?> clazz, String color) {
		StringBuilder description = new StringBuilder("[");
		EntityType<?> selectedEntity = null;
		selectedEntity = metamodel.entity(clazz);
		if (selectedEntity != null) {
			description.append(selectedEntity.getName());
			description.append('|');
			int i = 0;
			for (Attribute<?, ?> attribute : selectedEntity
					.getDeclaredAttributes()) {
				i++;
				description.append(attribute.getName());
				description.append(" : ");
				description.append(attribute.getJavaType().getSimpleName());
				if (i < selectedEntity.getDeclaredAttributes().size()) {
					description.append(';');
				}
			}
			if (color != null) {
				description.append("{bg:");
				description.append(color);
				description.append("}");
			}
			description.append("]");
		}
		return description.toString();
	}

	public static boolean entityExists(Metamodel metamodel, Class<?> clazz) {
		boolean result = true;
		try {
			metamodel.entity(clazz);
		} catch (IllegalArgumentException e) {
			result = false;
		}
		return result;
	}

	public static EntityType<?> getEntityFromClassName(Metamodel metamodel,
			String entityName) {
		Set<EntityType<?>> entities = metamodel.getEntities();
		for (EntityType<?> et : entities) {
			if (et.getName().equals(entityName)) {
				return et;
			}
		}
		return null;
	}
}
