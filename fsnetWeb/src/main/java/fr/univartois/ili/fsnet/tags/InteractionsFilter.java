package fr.univartois.ili.fsnet.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialGroup;

/**
 * 
 * @author Bouragba Mohamed
 * @author stephane gronowski
 * Check if the specified user have the specified right
 * 
 */
public class InteractionsFilter extends TagSupport {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5597573476354878781L;
	private SocialElement user;
	private Right right;

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		SocialGroup socialGroup;
		if(user == null || right == null){
			return SKIP_BODY;
		}
		
		socialGroup = user.getGroup();
		//no group, no rights
		if(socialGroup == null){
			return SKIP_BODY;
		}
		//super admin
		if(socialGroup.getGroup() == null && socialGroup.getMasterGroup().equals(user)){
			return EVAL_BODY_INCLUDE;
		}
		//regular rights
		if(socialGroup.isAuthorized(right)){
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
	}

	public SocialElement getUser() {
		return user;
	}

	public void setUser(SocialElement user) {
		this.user = user;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

}
