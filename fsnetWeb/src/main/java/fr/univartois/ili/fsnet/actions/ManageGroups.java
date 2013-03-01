package fr.univartois.ili.fsnet.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.ImageManager;
import fr.univartois.ili.fsnet.actions.utils.PictureType;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * @author FSNet
 * 
 */
public class ManageGroups extends ActionSupport implements CrudAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String GROUP_NAME_FORM_FIELD_NAME = "name";
	
	private static final String GROUP_ID_GROUP_ATTRIBUTE_NAME = "idGroup";
	
	private static final String SUCCES_ACTION_NAME = "success";
	private static final String FAILED_ACTION_NAME = "failed";
	private static final String TO_HOME_ACTION_NAME = "toHome";

	private String name;
	private String description;
	private int socialEntityId;
	private int parentId;
	private String[] memberListRight;
	private String[] rigthListRight;
	private String[] memberListLeft;
	private int id;
	private String color;
	private File Logo;//The actual file
	private String LogoContentType; //The content type of the file
	private String LogoFileName; //The uploaded file name
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#create(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String create(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		session.removeAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME);
		List<SocialEntity> allMembers = null;
		List<SocialGroup> allGroups = null;
		SocialGroup parentGroup = null;
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		if (socialEntityId >=0 && parentId >=0 && memberListRight != null && rigthListRight != null) {
			try {
				SocialEntity masterGroup = socialEntityFacade
						.getSocialEntity(socialEntityId);
				if (parentId >= 0) {
					parentGroup = socialGroupFacade.getSocialGroup(parentId);
				}
				List<SocialElement> socialElements = createSocialElement(em,
						memberListRight, masterGroup, parentGroup);

				SocialGroup socialGroup = socialGroupFacade.createSocialGroup(
						masterGroup, name, description, socialElements);

				socialGroup.addRights(getAcceptedRigth(rigthListRight));

				em.getTransaction().begin();
				em.persist(socialGroup);
				if (parentGroup != null) {
					parentGroup.addSocialElement(socialGroup);
					em.merge(parentGroup);
				}
				em.getTransaction().commit();

				name = "";
				description= "";
				socialEntityId = -1;
			} catch (RollbackException e) {
				addFieldError(GROUP_NAME_FORM_FIELD_NAME, "groups.name.exists");
				return FAILED_ACTION_NAME;
			} catch (NumberFormatException e) {
				return FAILED_ACTION_NAME;
			} finally {
				em.close();
			}
		} else {
			allMembers = getRefusedSocialMember(socialGroupFacade,
					socialEntityFacade.getAllSocialEntity(), null);
			request.setAttribute("allMembers", allMembers);
			request.setAttribute("refusedMembers", allMembers);
			request.setAttribute("refusedRigths", Right.values());
			allGroups = socialGroupFacade.getAllChildGroups(UserUtils
					.getHisGroup(request));
			request.setAttribute("allGroups", allGroups);
		}

		request.setAttribute(SUCCES_ACTION_NAME, getText("groups.success.on.create"));
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#modify(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String modify(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				em);
		
		
		try {
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

			SocialGroup socialGroup = socialGroupFacade.getSocialGroup(id);

			SocialEntity masterGroup = socialEntityFacade.getSocialEntity(Integer.valueOf(socialEntityId));
			SocialEntity oldMasterGroup = socialGroup.getMasterGroup();

			List<SocialElement> acceptedSocialEntity = createObjectSocialEntity(em, memberListRight);

			List<SocialElement> refusedSocialEntity = createObjectSocialEntity(em, memberListLeft);

			SocialGroup oldSocialGroup22 = masterGroup.getGroup();

			if (oldSocialGroup22 != null) {
				em.getTransaction().begin();
				oldSocialGroup22.removeSocialElement(masterGroup);
				em.merge(oldSocialGroup22);
				em.getTransaction().commit();
			}

			socialGroup.setName(name);
			socialGroup.setDescription(description);
			socialGroup.setMasterGroup(masterGroup);
			socialGroup.setRights(getAcceptedRigth(rigthListRight));
			socialGroup.removeAllSocialElements(refusedSocialEntity);
			socialGroup.addAllSocialElements(acceptedSocialEntity);
			socialGroup.addSocialElement(masterGroup);
			socialGroup.setColor(color);
			
			em.getTransaction().begin();
			em.merge(socialGroup);
			em.getTransaction().commit();

			request.getSession(true).removeAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME);
			if (!oldMasterGroup.equals(masterGroup)) {
				request.getSession().setAttribute("isMasterGroup", false);
				return TO_HOME_ACTION_NAME;
			}
			
			request.setAttribute(SUCCES_ACTION_NAME, getText("groups.success.on.modify"));
			
			if(user.getGroup().getId() == id){
				request.getSession().setAttribute("color", color);
			}
			
			name = "";
			description = "";
			socialEntityId = -1;
			color="";
			
			return SUCCESS;
		} catch (RollbackException e) {
			addFieldError(GROUP_NAME_FORM_FIELD_NAME, "groups.name.exists");
			return FAILED_ACTION_NAME;
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#delete(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String delete(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String switchState(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			String groupSelected = request.getParameter("groupSelected");
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			em.getTransaction().begin();
			int socialGroupId = Integer.parseInt(groupSelected);
			socialGroupFacade.switchState(socialGroupId);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#display(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String display(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			HttpSession session = request.getSession(true);

			List<SocialEntity> allMembers = null;
			List<SocialEntity> refusedMembers = null;

			List<SocialEntity> acceptedMembers = null;
			Set<Right> acceptedRigths = null;
			Set<Right> refusedRigths = new HashSet<Right>();

			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

			String idG = request.getParameter(GROUP_ID_GROUP_ATTRIBUTE_NAME);

			if (idG== null) {
				idG= (String) session.getAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME);
			} else {
				session.setAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME, id);
			}

			id=Integer.valueOf(idG);
			SocialGroup group = socialGroupFacade.getSocialGroup(id);
			SocialEntity masterGroup = group.getMasterGroup();
			SocialGroup parentGroup = group.getGroup();

			allMembers = socialEntityFacade.getAllSocialEntity();

			acceptedMembers = socialGroupFacade
					.getAcceptedSocialElementsByFilter(group,
							SocialEntity.class);
			refusedMembers = getRefusedSocialMember(socialGroupFacade,
					allMembers, group);
			acceptedRigths = group.getRights();
			for (Right right : Right.values()) {
				refusedRigths.add(right);
			}

			refusedRigths.removeAll(acceptedRigths);
			
			name= group.getName();
			description=group.getDescription();
			if (parentGroup != null) {
				request.setAttribute("nameParent", parentGroup.getName());
			}
			request.setAttribute("masterGroup", masterGroup);

			request.setAttribute("acceptedMembers", acceptedMembers);
			request.setAttribute("allMembers", getSimpleMember(em, group));

			request.setAttribute("refusedMembers", refusedMembers);

			request.setAttribute("refusedRigths", refusedRigths);
			request.setAttribute("acceptedRigths", acceptedRigths);

			return SUCCESS;
		} catch (NumberFormatException e) {
			return TO_HOME_ACTION_NAME;
		} finally {
			em.close();
		}

	}

	/**
	 * @param groupsAccepted
	 * @return
	 */
	private Set<Right> getAcceptedRigth(String[] groupsAccepted) {
		Set<Right> rights = new HashSet<Right>();

		for (String string : groupsAccepted){
			rights.add(Right.valueOf(string));
		}

		return rights;
	}

	/**
	 * @param em
	 * @param membersAccepted
	 * @param masterGroup
	 * @param parentGroup
	 * @return
	 */
	private List<SocialElement> createSocialElement(EntityManager em,
			String[] membersAccepted, SocialEntity masterGroup,
			SocialGroup parentGroup) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		for (String members : membersAccepted) {
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(members)));
		}

		if (!socialElements.contains(masterGroup)) {
			socialElements.add(masterGroup);
		}
		if (parentGroup != null) {
			socialElements.remove(parentGroup);
		}
		return socialElements;
	}

	/**
	 * @param em
	 * @param members
	 * @return
	 */
	private List<SocialElement> createObjectSocialEntity(EntityManager em,
			String[] members) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		for (String member : members) {
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(member)));
		}

		return socialElements;
	}

	/**
	 * @param sgf
	 * @param allMembers
	 * @param socialGroup
	 * @return
	 */
	private List<SocialEntity> getRefusedSocialMember(SocialGroupFacade sgf,
			List<SocialEntity> allMembers, SocialGroup socialGroup) {
		if (sgf == null) {
			throw new IllegalArgumentException();
		}
		List<SocialEntity> resulEntities = new ArrayList<SocialEntity>();
		List<SocialEntity> members = allMembers;
		if (socialGroup != null) {
			members.removeAll(sgf.getAcceptedSocialElementsByFilter(
					socialGroup, SocialEntity.class));
		}
		for (SocialEntity se : allMembers) {
			if (se.getGroup() == null) {
				resulEntities.add(se);
			}
		}
		return resulEntities;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#search(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String search(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialGroup socialGroup;
		try {
			socialGroup = UserUtils.getHisGroup(request);
			List<SocialGroup> resultOthersList = socialGroupFacade
					.getAllChildGroups(socialGroup);

			request.setAttribute("groupsList", resultOthersList);
			return SUCCESS;
		} catch (NullPointerException e) {
			return TO_HOME_ACTION_NAME;
		}
	}

	/**
	 * @param em
	 * @param socialGroup
	 * @return
	 */
	private List<SocialEntity> getSimpleMember(EntityManager em,
			SocialGroup socialGroup) {
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		List<SocialEntity> allMastersGroup = socialGroupFacade
				.getAllMasterGroupes();

		List<SocialEntity> allOrphanMembers = socialEntityFacade
				.getAllOrphanMembers();

		List<SocialEntity> allSocialchildEntity = socialGroupFacade
				.getAllChildMembers(socialGroup);

		allSocialchildEntity.removeAll(allMastersGroup);
		allSocialchildEntity.add(socialGroup.getMasterGroup());
		allSocialchildEntity.addAll(allOrphanMembers);

		return allSocialchildEntity;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String displayInformationGroup(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			EntityManager em = PersistenceProvider.createEntityManager();

			String idGroup = request
					.getParameter(GROUP_ID_GROUP_ATTRIBUTE_NAME);
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			SocialGroup socialGroup;
			
			if (idGroup == null || idGroup.isEmpty()) {
				socialGroup = null;
			} else {
				int id = Integer.valueOf(idGroup);
				socialGroup = socialGroupFacade.getSocialGroup(id);
			}
			
			request.setAttribute("socialGroup", socialGroup);

			List<SocialEntity> allMembers = socialGroupFacade
					.getMembersFromGroup(socialGroup);
			request.setAttribute("allMembers", allMembers);

			List<SocialGroup> listOfAntecedantGroup = socialGroupFacade
					.getAllAntecedentSocialGroups(socialGroup);
			java.util.Collections.reverse(listOfAntecedantGroup);
			request.setAttribute("antecedantsOfGroup", listOfAntecedantGroup);

			List<SocialGroup> listOfChildGroup = socialGroupFacade
					.getAllChildGroups(socialGroup);
			listOfChildGroup.remove(socialGroup);
			request.setAttribute("childsOfGroup", listOfChildGroup);
		} catch (NumberFormatException e) {

		}

		return SUCCESS;
	}

	/**
	 * @param request
	 * @param key
	 */
	private void sendPictureError(HttpServletRequest request, String key) {
		addFieldError("Logo", getText(key));
	}

	private static final int MAX_PICTURE_SIZE = 500000;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String changeLogo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE)) {
			return "unauthorized";
		}

		int groupId = UserUtils.getHisGroup(request).getId();
		
		if (Logo.length()!= 0) {
			PictureType pictureType = null;
			for (PictureType pt : PictureType.values()) {
				if (pt.getMimeType().equals(LogoContentType)) {
					pictureType = pt;
					break;
				}
			}
			
			if (pictureType != null) {

				if (Logo.length() > MAX_PICTURE_SIZE) {
					sendPictureError(request, "groups.logo.maxsize");
					return SUCCESS;
				}

				try {
					ImageManager.createLogo(groupId, new FileInputStream(Logo),
							pictureType);
				} catch (FileNotFoundException e) {
					sendPictureError(request, "groups.logo.fatal");
				} catch (IOException e) {
					sendPictureError(request, "groups.logo.fatal");
				} catch (IllegalStateException e) {
					sendPictureError(request, "groups.logo.fatal");
				}
			} else {
				sendPictureError(request, "groups.logo.type");
			}
		}
		
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSocialEntityId() {
		return socialEntityId;
	}

	public void setSocialEntityId(int socialEntityId) {
		this.socialEntityId = socialEntityId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String[] getMemberListRight() {
		return memberListRight;
	}

	public void setMemberListRight(String[] memberListRight) {
		this.memberListRight = memberListRight;
	}

	public String[] getRigthListRight() {
		return rigthListRight;
	}

	public void setRigthListRight(String[] rigthListRight) {
		this.rigthListRight = rigthListRight;
	}

	public String[] getMemberListLeft() {
		return memberListLeft;
	}

	public void setMemberListLeft(String[] memberListLeft) {
		this.memberListLeft = memberListLeft;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public File getLogo() {
		return Logo;
	}

	public void setLogo(File logo) {
		Logo = logo;
	}

	public String getLogoContentType() {
		return LogoContentType;
	}

	public void setLogoContentType(String logoContentType) {
		LogoContentType = logoContentType;
	}

	public String getLogoFileName() {
		return LogoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		LogoFileName = logoFileName;
	}
	
	
}
