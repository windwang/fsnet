package fr.univartois.ili.fsnet.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialGroup;

/**
 * 
 * @author Bouragba Mohamed
 * Check if you have the right
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
		// TODO Auto-generated method stub
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		SocialGroup socialGroup;
		boolean isAuthorized;
		if(user == null || right == null)
			return SKIP_BODY;
		socialGroup = user.getGroup();
		if(socialGroup == null)
			return SKIP_BODY;
		isAuthorized = socialGroup.isAuthorized(right);
		if(!isAuthorized)
			return SKIP_BODY;
		return EVAL_BODY_INCLUDE;
	}

	public SocialElement getUser() {
		return user;
	}

	public void setUser(SocialElement user) {
		System.out.println("InteractionsFilter.setUser()");
		this.user = user;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		System.out.println("InteractionsFilter.setRight()");
		this.right = right;
	}

}
